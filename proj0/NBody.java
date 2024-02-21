public class NBody {
    public static double readRadius(String filename) {
        In infile = new In(filename);
        int number = infile.readInt();
        double radius = infile.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String filename) {
        In infile = new In(filename);
        int number = infile.readInt();
        double radius = infile.readDouble();
        Planet[] planets = new Planet[number];
        for (int i = 0; i < number; i++) {
            double xCor = infile.readDouble();
            double yCor = infile.readDouble();
            double xVel = infile.readDouble();
            double yVel = infile.readDouble();
            double mass = infile.readDouble();
            String image = infile.readString();
            planets[i] = new Planet(xCor, yCor, xVel, yVel, mass, image);
        }
        return planets;
    }

    public static void main(String[] args) {
        // read from file, collect all needed input
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        // set the background
        int numPlanets = planets.length;
        String imagePath = "images/starfield.jpg";
        StdDraw.setScale(-radius, radius);
        StdDraw.enableDoubleBuffering();

        // create animation
        double time = 0;
        while (time < T) {
            double[] xForces = new double[numPlanets];
            double[] yForces = new double[numPlanets];
            for (int i = 0; i < numPlanets; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < numPlanets; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            // draw planets for every dt seconds
            StdDraw.picture(0, 0, imagePath);
            for (Planet planet : planets) {
                planet.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }

        // print output
        StdOut.printf("%d\n", numPlanets);
        StdOut.printf("%.2e\n", radius);
        for (Planet planet : planets) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planet.xxPos, planet.yyPos, planet.xxVel,
                    planet.yyVel, planet.mass, planet.imgFileName);
        }
    }
}
