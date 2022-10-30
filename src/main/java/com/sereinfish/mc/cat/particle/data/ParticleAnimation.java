package com.sereinfish.mc.cat.particle.data;

import java.util.ArrayList;

public class ParticleAnimation {
    public ArrayList<Frame> frames = new ArrayList<>();

    public ParticleAnimation() {
    }

    public ParticleAnimation(ArrayList<Frame> frames) {
        this.frames = frames;
    }

    public void addFrame(Frame frame){
        frames.add(frame);
    }

    public void removeFrame(int index){
        frames.remove(index);
    }

    @Override
    public String toString() {
        return "ParticleAnimation{" +
                "frames=" + frames.size() +
                '}';
    }

    public static class Frame{
        public long delay = 1000L;
        public ArrayList<ParticleNBT> data;

        public Frame(long delay) {
            this.delay = delay;
            data = new ArrayList<>();
        }

        public Frame(long delay, ArrayList<ParticleNBT> data) {
            this.delay = delay;
            this.data = data;
        }
    }
}
