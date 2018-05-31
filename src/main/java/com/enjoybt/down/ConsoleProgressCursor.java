package com.enjoybt.down;

public class ConsoleProgressCursor {

	private static String CURSOR_STRING = "0%.......10%.......20%.......30%.......40%.......50%.......60%.......70%.......80%.......90%.....100%";

    private double max = 0.;
    private double cursor = 1.;
    private double lastCursor = 0.;

	public static void main(String[] args) throws InterruptedException {
		final int max = 100;
        ConsoleProgressCursor progress = new ConsoleProgressCursor("Progress : ", max);
        for (int i = 0; i < max; i++) {
            Thread.sleep(10L);
            progress.nextProgress();
        }
        progress.endProgress();
	}

	public ConsoleProgressCursor(String title, double maxCounts) {
        max = maxCounts;
        System.out.print(title);
    }

    public void nextProgress() {
        cursor += 100. / max;
        printCursor();
    }

    public void nextProgress(double progress) {
        cursor += progress * 100. / max;

        if (cursor > 101) {
        	cursor = 101;
        }
        printCursor();
    }

    public void endProgress() {
        System.out.println(CURSOR_STRING.substring((int) lastCursor, 101));
        System.out.println();
    }

    private void printCursor() {
        final int intCursor = (int) Math.floor(cursor);
        System.out.print(CURSOR_STRING.substring((int) lastCursor, intCursor));
        lastCursor = cursor;
    }
}
