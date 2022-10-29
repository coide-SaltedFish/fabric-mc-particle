package com.sereinfish.mc.cat.particle.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.sereinfish.mc.cat.particle.Start;
import com.sereinfish.mc.cat.particle.thread.ParticleManager;
import com.sereinfish.mc.cat.particle.thread.PlayerParticleSlotRunnable;
import com.sereinfish.mc.cat.particle.untils.Utils;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

import java.util.Map;

public class ModCommand {
    private static final String errTip = "[SereinFishMod] 糟糕，出现了意料之外的错误，请联系服务器管理员";

    private ModCommand(){

    }

    public static void init(){
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment)->{
            dispatcher.register(CommandManager.literal("SFParticle")
                    .then(CommandManager.literal("enable")
                            .then(CommandManager.literal("off").executes(context -> {
                                try {
                                    Utils.particleConfig.get(context.getSource().getPlayerOrThrow()).enable = false;
                                    context.getSource().getPlayerOrThrow().sendMessage(MutableText.of(new LiteralTextContent("已关闭全局特殊粒子效果显示")).formatted(Formatting.GREEN));
                                } catch (Exception e) {
                                    Start.logger.error("命令执行错误", e);
                                    throw new SimpleCommandExceptionType(MutableText.of(new LiteralTextContent(errTip)).formatted(Formatting.RED)).create();
                                }
                                return Command.SINGLE_SUCCESS;
                            }))

                            .then(CommandManager.literal("on").executes(context -> {
                                try {
                                    Utils.particleConfig.get(context.getSource().getPlayerOrThrow()).enable = true;
                                    context.getSource().getPlayerOrThrow().sendMessage(MutableText.of(new LiteralTextContent("已开启全局特殊粒子效果显示")).formatted(Formatting.GREEN));
                                } catch (Exception e) {
                                    Start.logger.error("命令执行错误", e);
                                    throw new SimpleCommandExceptionType(MutableText.of(new LiteralTextContent(errTip)).formatted(Formatting.RED)).create();
                                }
                                return Command.SINGLE_SUCCESS;
                            }))
                    )

                    .then(CommandManager.literal("otherEnable")
                            .then(CommandManager.literal("off").executes(context -> {
                                try {
                                    Utils.particleConfig.get(context.getSource().getPlayerOrThrow()).otherEnable = false;
                                    context.getSource().getPlayerOrThrow().sendMessage(MutableText.of(new LiteralTextContent("已关闭其他玩家特殊粒子效果显示")).formatted(Formatting.GREEN));
                                } catch (Exception e) {
                                    Start.logger.error("命令执行错误", e);
                                    throw new SimpleCommandExceptionType(MutableText.of(new LiteralTextContent(errTip)).formatted(Formatting.RED)).create();
                                }
                                return Command.SINGLE_SUCCESS;
                            }))

                            .then(CommandManager.literal("on").executes(context -> {
                                try {
                                    Utils.particleConfig.get(context.getSource().getPlayerOrThrow()).otherEnable = true;
                                    context.getSource().getPlayerOrThrow().sendMessage(MutableText.of(new LiteralTextContent("已开启其他玩家特殊粒子效果显示")).formatted(Formatting.GREEN));
                                } catch (Exception e) {
                                    Start.logger.error("命令执行错误", e);
                                    throw new SimpleCommandExceptionType(MutableText.of(new LiteralTextContent(errTip)).formatted(Formatting.RED)).create();
                                }
                                return Command.SINGLE_SUCCESS;
                            }))
                    )

                    .then(CommandManager.literal("onlyOwnEnable")
                            .then(CommandManager.literal("off").executes(context -> {
                                try {
                                    Utils.particleConfig.get(context.getSource().getPlayerOrThrow()).onlyOwnEnable = false;
                                    context.getSource().getPlayerOrThrow().sendMessage(MutableText.of(new LiteralTextContent("已向其他玩家开放自己的粒子效果")).formatted(Formatting.GREEN));
                                } catch (Exception e) {
                                    Start.logger.error("命令执行错误", e);
                                    throw new SimpleCommandExceptionType(MutableText.of(new LiteralTextContent(errTip)).formatted(Formatting.RED)).create();
                                }
                                return Command.SINGLE_SUCCESS;
                            }))

                            .then(CommandManager.literal("on").executes(context -> {
                                try {
                                    Utils.particleConfig.get(context.getSource().getPlayerOrThrow()).onlyOwnEnable = true;
                                    context.getSource().getPlayerOrThrow().sendMessage(MutableText.of(new LiteralTextContent("已向其他玩家关闭自己的粒子效果")).formatted(Formatting.GREEN));
                                } catch (Exception e) {
                                    Start.logger.error("命令执行错误", e);
                                    throw new SimpleCommandExceptionType(MutableText.of(new LiteralTextContent(errTip)).formatted(Formatting.RED)).create();
                                }
                                return Command.SINGLE_SUCCESS;
                            }))
                    )
                    .then(CommandManager.literal("onlyOtherEnable")
                            .then(CommandManager.literal("off").executes(context -> {
                                try {
                                    Utils.particleConfig.get(context.getSource().getPlayerOrThrow()).onlyOtherEnable = false;
                                    context.getSource().getPlayerOrThrow().sendMessage(MutableText.of(new LiteralTextContent("已启用自己的粒子效果")).formatted(Formatting.GREEN));
                                } catch (Exception e) {
                                    Start.logger.error("命令执行错误", e);
                                    throw new SimpleCommandExceptionType(MutableText.of(new LiteralTextContent(errTip)).formatted(Formatting.RED)).create();
                                }
                                return Command.SINGLE_SUCCESS;
                            }))

                            .then(CommandManager.literal("on").executes(context -> {
                                try {
                                    Utils.particleConfig.get(context.getSource().getPlayerOrThrow()).onlyOtherEnable = true;
                                    context.getSource().getPlayerOrThrow().sendMessage(MutableText.of(new LiteralTextContent("已取消显示自己的粒子效果")).formatted(Formatting.GREEN));
                                } catch (Exception e) {
                                    Start.logger.error("命令执行错误", e);
                                    throw new SimpleCommandExceptionType(MutableText.of(new LiteralTextContent(errTip)).formatted(Formatting.RED)).create();
                                }
                                return Command.SINGLE_SUCCESS;
                            }))
                    )

                    .then(CommandManager.literal("prefixEnable")
                            .then(CommandManager.literal("off").executes(context -> {
                                try {
                                    Utils.particleConfig.get(context.getSource().getPlayerOrThrow()).prefixEnable = false;
                                    context.getSource().getPlayerOrThrow().sendMessage(MutableText.of(new LiteralTextContent("已关闭前缀显示")).formatted(Formatting.GREEN));
                                } catch (Exception e) {
                                    Start.logger.error("命令执行错误", e);
                                    throw new SimpleCommandExceptionType(MutableText.of(new LiteralTextContent(errTip)).formatted(Formatting.RED)).create();
                                }
                                return Command.SINGLE_SUCCESS;
                            }))

                            .then(CommandManager.literal("on").executes(context -> {
                                try {
                                    Utils.particleConfig.get(context.getSource().getPlayerOrThrow()).prefixEnable = true;
                                    context.getSource().getPlayerOrThrow().sendMessage(MutableText.of(new LiteralTextContent("已启用前缀显示")).formatted(Formatting.GREEN));
                                } catch (Exception e) {
                                    Start.logger.error("命令执行错误", e);
                                    throw new SimpleCommandExceptionType(MutableText.of(new LiteralTextContent(errTip)).formatted(Formatting.RED)).create();
                                }
                                return Command.SINGLE_SUCCESS;
                            }))
                    )

                    .then(CommandManager.literal("v").executes(context -> {
                        try {
                            context.getSource().getPlayerOrThrow().sendMessage(MutableText.of(new LiteralTextContent("当前版本：" + Utils.version)).formatted(Formatting.GREEN));
                        } catch (Exception e) {
                            Start.logger.error("命令执行错误", e);
                            throw new SimpleCommandExceptionType(MutableText.of(new LiteralTextContent(errTip)).formatted(Formatting.RED)).create();
                        }
                        return Command.SINGLE_SUCCESS;
                    }))

                    .then(CommandManager.literal("debugInfo").executes(context -> {
                        try {
                            ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
                            player.sendMessage(MutableText.of(new LiteralTextContent("------Mod测试信息------")).formatted(Formatting.GREEN));
                            player.sendMessage(MutableText.of(new LiteralTextContent("particleContextScope:" + ParticleManager.INSTANCE.particleContextScopeIsActive())).formatted(Formatting.GREEN));
                            player.sendMessage(MutableText.of(new LiteralTextContent("玩家：" + player.getEntityName() + "[" + player.getUuidAsString() + "]")).formatted(Formatting.GREEN));

                            Map<EquipmentSlot, PlayerParticleSlotRunnable> map = ParticleManager.INSTANCE.getMap().get(player.getUuidAsString());

                            player.sendMessage(MutableText.of(new LiteralTextContent(map.get(EquipmentSlot.MAINHAND).debugInfo())).formatted(Formatting.GREEN));
                            player.sendMessage(MutableText.of(new LiteralTextContent(map.get(EquipmentSlot.OFFHAND).debugInfo())).formatted(Formatting.GREEN));
                            player.sendMessage(MutableText.of(new LiteralTextContent(map.get(EquipmentSlot.HEAD).debugInfo())).formatted(Formatting.GREEN));
                            player.sendMessage(MutableText.of(new LiteralTextContent(map.get(EquipmentSlot.CHEST).debugInfo())).formatted(Formatting.GREEN));
                            player.sendMessage(MutableText.of(new LiteralTextContent(map.get(EquipmentSlot.FEET).debugInfo())).formatted(Formatting.GREEN));
                            player.sendMessage(MutableText.of(new LiteralTextContent(map.get(EquipmentSlot.LEGS).debugInfo())).formatted(Formatting.GREEN));
                        } catch (Exception e) {
                            Start.logger.error("命令执行错误", e);
                            throw new SimpleCommandExceptionType(MutableText.of(new LiteralTextContent(errTip)).formatted(Formatting.RED)).create();
                        }
                        return Command.SINGLE_SUCCESS;
                    }))
            );
        });
    }
}
