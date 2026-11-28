package com.chinaex123.redstone_enchants.util;

import com.chinaex123.redstone_enchants.event.EnchantContext;
import com.mojang.math.Transformation;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

/**
 * 冰冻动画：由 13 个方块展示实体拼出的冻结画面。
 * <p>
 * 动画分三步：生成薄冰 → 冰柱升起 → 冰柱落下并消失；全部用 Java 对象与标签构造，
 * 不再使用 SNBT 字符串。
 */
public final class FreezePicAnimation {

    /** 冰面所用方块：age=1 的霜冰 */
    private static final BlockState ICE_BLOCK = Blocks.FROSTED_ICE.defaultBlockState().setValue(FrostedIceBlock.AGE, 1);
    /** 展示实体标签：仍在播放的冰柱 */
    private static final String TAG_FREEZING = "redstone_enchants.block_display.freezing";
    /** 展示实体标签：处于第一阶段 */
    private static final String TAG_FIRST = "redstone_enchants.block_display.animation.first";
    /** 展示实体标签：处于第二阶段 */
    private static final String TAG_SECOND = "redstone_enchants.block_display.animation.second";
    /** 展示实体标签：已经播放完毕，等待清除 */
    private static final String TAG_FINISHED = "redstone_enchants.block_display.animation.finished";
    /** 薄冰阶段的平移（贴地） */
    private static final Vector3f FLAT_TRANSLATION = new Vector3f(0.0F, -0.25F, 0.0F);
    /** 落下阶段的平移（再往下半格） */
    private static final Vector3f FADED_TRANSLATION = new Vector3f(0.0F, -0.5F, 0.0F);
    /** 冰块完全压平后的纵向缩放 */
    private static final float FLAT_SCALE_Y = 0.0F;
    /** 生成后多少刻进入第一阶段 */
    private static final int FIRST_STEP_DELAY = 2;
    /** 第一阶段持续多少刻 */
    private static final int SECOND_STEP_DELAY = 30;
    /** 第二阶段持续多少刻 */
    private static final int FINISHED_DELAY = 18;
    /** 每个冰柱播放的角色音效 */
    private static final float SOUND_VOLUME = 2.0F;
    /** 音效音调（压到极低） */
    private static final float SOUND_PITCH = 0.01F;
    /** 冰面粒子颜色 */
    private static final Vector3f DUST_COLOR = new Vector3f(0.65F, 0.8F, 1.0F);
    /** 冰面粒子缩放 */
    private static final float DUST_SCALE = 1.0F;
    /** 冰面粒子数量 */
    private static final int DUST_COUNT = 50;
    /** 冰面粒子生成高度偏移 */
    private static final double DUST_OFFSET_Y = 0.25D;
    /** 冰面粒子水平扩散 */
    private static final double DUST_SPREAD = 0.85D;
    /** 冰面粒子速度 */
    private static final double DUST_SPEED = 0.85D;

    /**
     * 一块冰柱的三段形态。
     *
     * @param leftRotation  左四元数
     * @param rightRotation 右四元数
     * @param scaleX        水平 X 缩放
     * @param scaleZ        水平 Z 缩放
     * @param liftedScaleY  升起后的纵向缩放
     * @param liftDuration  升起补间时长（刻）
     * @param fadeDuration  落下补间时长（刻）
     */
    private record Frame(Quaternionf leftRotation, Quaternionf rightRotation,
                         float scaleX, float scaleZ, float liftedScaleY,
                         int liftDuration, int fadeDuration) {
    }


    /** 第 0 块冰 */
    private static final Frame FRAME_0 = new Frame(new Quaternionf(0.20761608f, -0.1768769f, -0.22127223f, 0.93629533f),
            new Quaternionf(-0.0119385645f, 0.4739189f, -0.008892587f, 0.88044274f), 0.48877987f, 0.4082154f, 2.1972475f, 6, 8);
    /** 第 1 块冰 */
    private static final Frame FRAME_1 = new Frame(new Quaternionf(0.27827242f, 0.21906535f, -0.027267985f, 0.9347895f),
            new Quaternionf(0.008432141f, 0.07245588f, 0.020641554f, -0.9971224f), 0.30155635f, 0.39867896f, 1.9095109f, 3, 4);
    /** 第 2 块冰 */
    private static final Frame FRAME_2 = new Frame(new Quaternionf(0.27827242f, 0.21906535f, -0.027267985f, 0.9347895f),
            new Quaternionf(0.008432141f, 0.07245588f, 0.020641554f, -0.9971224f), 0.30155635f, 0.39867896f, 1.9095109f, 7, 6);
    /** 第 3 块冰 */
    private static final Frame FRAME_3 = new Frame(new Quaternionf(0.3035407f, -0.23713717f, 0.038992405f, 0.92201346f),
            new Quaternionf(-0.008808866f, 0.09265068f, -0.02308758f, 0.995392f), 0.42169607f, 0.36769652f, 1.7484765f, 8, 4);
    /** 第 4 块冰 */
    private static final Frame FRAME_4 = new Frame(new Quaternionf(0.20761608f, -0.1768769f, -0.22127223f, 0.93629533f),
            new Quaternionf(-0.0119385645f, 0.4739189f, -0.008892587f, 0.88044274f), 0.48877987f, 0.4082154f, 2.1972475f, 9, 12);
    /** 第 5 块冰 */
    private static final Frame FRAME_5 = new Frame(new Quaternionf(0.0644984f, 0.33012953f, -0.2966116f, 0.89379865f),
            new Quaternionf(-0.012078595f, 0.47392493f, -0.008939332f, 0.8804371f), 0.4887792f, 0.40821457f, 2.1972423f, 10, 3);
    /** 第 6 块冰 */
    private static final Frame FRAME_6 = new Frame(new Quaternionf(-0.041160293f, 0.01865346f, -0.09304882f, 0.9946356f),
            new Quaternionf(-0.03366559f, 0.055936594f, -0.020904878f, 0.99764764f), 0.5694376f, 0.48386317f, 1.5423458f, 12, 6);
    /** 第 7 块冰 */
    private static final Frame FRAME_7 = new Frame(new Quaternionf(0.17247608f, -0.6083231f, 0.44446576f, -0.6345433f),
            new Quaternionf(-0.073000684f, -0.0020624977f, -0.08319552f, 0.9938537f), 0.51876557f, 0.564991f, 1.7447492f, 7, 3);
    /** 第 8 块冰 */
    private static final Frame FRAME_8 = new Frame(new Quaternionf(0.23646699f, -0.67403525f, 0.18719941f, 0.6743265f),
            new Quaternionf(0.020685628f, 0.26502955f, 0.012503981f, -0.96393734f), 0.35222408f, 0.47451732f, 1.9584135f, 6, 8);
    /** 第 9 块冰 */
    private static final Frame FRAME_9 = new Frame(new Quaternionf(-0.08416754f, 0.61440694f, -0.2348772f, 0.74850035f),
            new Quaternionf(-0.009980634f, -0.19441357f, -0.054356012f, 0.9793617f), 0.3508664f, 0.2285353f, 1.1267037f, 5, 10);
    /** 第 10 块冰 */
    private static final Frame FRAME_10 = new Frame(new Quaternionf(-0.13719803f, 0.25688514f, -0.1243474f, 0.9485381f),
            new Quaternionf(-0.020792069f, 0.17890018f, 0.023349473f, 0.9833704f), 0.5010639f, 0.46192458f, 1.0566843f, 4, 5);
    /** 第 11 块冰 */
    private static final Frame FRAME_11 = new Frame(new Quaternionf(-0.1793692f, 0.82914215f, -0.21142682f, 0.4854368f),
            new Quaternionf(-0.024937697f, -0.1498754f, -0.0409054f, 0.9875436f), 0.28164324f, 0.22289199f, 0.0f, 3, 8);
    /** 第 12 块冰 */
    private static final Frame FRAME_12 = new Frame(new Quaternionf(-0.24112663f, 0.89863044f, -0.15484048f, 0.33218348f),
            new Quaternionf(-0.035565913f, -0.070613824f, -0.034442298f, 0.9962744f), 0.50525606f, 0.41262737f, 1.611846f, 4, 12);

    /** 13 块冰的动画数据 */
    private static final Frame[] FRAMES = {
            FRAME_0,
            FRAME_1,
            FRAME_2,
            FRAME_3,
            FRAME_4,
            FRAME_5,
            FRAME_6,
            FRAME_7,
            FRAME_8,
            FRAME_9,
            FRAME_10,
            FRAME_11,
            FRAME_12
    };

    private FreezePicAnimation() {
    }

    /**
     * 播放整段冰冻动画。
     *
     * @param context 附魔上下文（执行位置即动画中心）
     */
    public static void play(EnchantContext context) {
        ServerLevel level = context.level();
        for (int index = 0; index < FRAMES.length; index++) {
            Frame frame = FRAMES[index];
            Display.BlockDisplay display = new Display.BlockDisplay(EntityType.BLOCK_DISPLAY, level);
            CompoundTag tag = new CompoundTag();
            tag.put("block_state", NbtUtils.writeBlockState(ICE_BLOCK));
            tag.put("transformation", encodeTransformation(flat(frame)));
            display.load(tag);
            display.addTag(TAG_FREEZING);
            display.addTag(TAG_FIRST);
            display.addTag(freezeTag(index));
            context.spawn(display);
        }
        context.sound(SoundEvents.AMETHYST_BLOCK_STEP, SOUND_VOLUME, SOUND_PITCH);
        context.sound(SoundEvents.AMETHYST_BLOCK_BREAK, SOUND_VOLUME, SOUND_PITCH);
        context.sound(SoundEvents.AMETHYST_BLOCK_FALL, SOUND_VOLUME, SOUND_PITCH);
        context.schedule(FIRST_STEP_DELAY, () -> firstStep(context));
    }

    /**
     * 第一阶段：冰柱升起，同时播放音效与冰雾粒子。
     */
    private static void firstStep(EnchantContext context) {
        ServerLevel level = context.level();
        for (Entity display : EnchantContext.allWithTag(level, TAG_FREEZING)) {
            level.playSound(null, display.getX(), display.getY(), display.getZ(),
                    SoundEvents.AMETHYST_BLOCK_FALL, SoundSource.PLAYERS, SOUND_VOLUME, SOUND_PITCH);
            level.sendParticles(new DustParticleOptions(DUST_COLOR, DUST_SCALE),
                    display.getX(), display.getY() + DUST_OFFSET_Y, display.getZ(),
                    DUST_COUNT, DUST_SPREAD, 0.0D, DUST_SPREAD, DUST_SPEED);
        }
        for (int index = 0; index < FRAMES.length; index++) {
            Frame frame = FRAMES[index];
            for (Entity display : EnchantContext.allWithTag(level, freezeTag(index))) {
                if (display.getTags().contains(TAG_FIRST)) {
                    display.removeTag(TAG_FIRST);
                    display.addTag(TAG_SECOND);
                    context.mergeTag(display, stepTag(lifted(frame), frame.liftDuration()));
                }
            }
        }
        context.schedule(SECOND_STEP_DELAY, () -> secondStep(context));
    }

    /**
     * 第二阶段：冰柱压回地面并淡出。
     */
    private static void secondStep(EnchantContext context) {
        ServerLevel level = context.level();
        for (int index = 0; index < FRAMES.length; index++) {
            Frame frame = FRAMES[index];
            for (Entity display : EnchantContext.allWithTag(level, freezeTag(index))) {
                display.removeTag(TAG_FREEZING);
                display.removeTag(TAG_SECOND);
                display.removeTag(freezeTag(index));
                display.addTag(TAG_FINISHED);
                context.mergeTag(display, stepTag(faded(frame), frame.fadeDuration()));
            }
        }
        context.schedule(FINISHED_DELAY, () -> finished(context));
    }

    /**
     * 收尾：清除所有播放完毕的展示实体。
     */
    private static void finished(EnchantContext context) {
        List<Entity> displays = EnchantContext.allWithTag(context.level(), TAG_FINISHED);
        for (Entity display : displays) {
            display.discard();
        }
    }

    private static CompoundTag stepTag(Transformation transformation, int duration) {
        CompoundTag tag = new CompoundTag();
        tag.put("transformation", encodeTransformation(transformation));
        tag.putInt("interpolation_duration", duration);
        tag.putInt("start_interpolation", 0);
        return tag;
    }

    private static net.minecraft.nbt.Tag encodeTransformation(Transformation transformation) {
        return Transformation.CODEC.encodeStart(NbtOps.INSTANCE, transformation).getOrThrow();
    }

    /** 贴地形态：纵向压平 */
    private static Transformation flat(Frame frame) {
        return new Transformation(FLAT_TRANSLATION, frame.leftRotation(),
                new Vector3f(frame.scaleX(), FLAT_SCALE_Y, frame.scaleZ()), frame.rightRotation());
    }

    /** 升起形态：纵向拉到目标高度 */
    private static Transformation lifted(Frame frame) {
        return new Transformation(FLAT_TRANSLATION, frame.leftRotation(),
                new Vector3f(frame.scaleX(), frame.liftedScaleY(), frame.scaleZ()), frame.rightRotation());
    }

    /** 落下形态：再次压平并整体下移半格 */
    private static Transformation faded(Frame frame) {
        return new Transformation(FADED_TRANSLATION, frame.leftRotation(),
                new Vector3f(frame.scaleX(), FLAT_SCALE_Y, frame.scaleZ()), frame.rightRotation());
    }

    /** 第 index 块冰的独立标签 */
    private static String freezeTag(int index) {
        return TAG_FREEZING + "_" + index;
    }
}
