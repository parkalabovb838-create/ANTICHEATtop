package com.good.anticheat;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DatasetCollector {

    private final AntiCheatPlugin plugin;
    private boolean running = false;
    private BukkitRunnable task;
    private long startTime;
    private static final long DURATION = 10 * 60 * 1000L;

    public DatasetCollector(AntiCheatPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean isRunning() { return running; }

    public void start() {
        if (running) return;
        running = true;
        startTime = System.currentTimeMillis();

        File file = new File(plugin.getDataFolder(), "dataset.csv");
        try (FileWriter fw = new FileWriter(file, false)) {
            fw.write("yaw,pitch,angle,cps,gcd,reach,ai,vl,ping,timestamp\n");
        } catch (IOException e) {
            plugin.getLogger().warning("Dataset: " + e.getMessage());
        }

        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (!running) { cancel(); return; }