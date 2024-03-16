import synthesizer.GuitarString;

public class GuitarHero {
    private static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static final int LENGTH = KEYBOARD.length();
    private static GuitarString[] concert = new GuitarString[LENGTH];

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        for (int i = 0; i < LENGTH; i++) {
            concert[i] = new GuitarString(440.0 * Math.pow(2, (i - 24) / 12.0));
        }

        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = KEYBOARD.indexOf(key);
                if (index != -1) {
                    concert[index].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (int i = 0; i < LENGTH; i++) {
                sample += concert[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < LENGTH; i++) {
                concert[i].tic();
            }
        }
    }
}
