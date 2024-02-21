public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double gravitation = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet P) {
        this.xxPos = P.xxPos;
        this.yyPos = P.yyPos;
        this.xxVel = P.xxVel;
        this.yyVel = P.yyVel;
        this.mass = P.mass;
        this.imgFileName = P.imgFileName;
    }

    /**
     * Calculate the distance between two planets.
     * @param P another planet instance.
     * @return the distance represented in double.
     */
    public double calcDistance(Planet P) {
        double xDiff = this.xxPos - P.xxPos;
        double yDiff = this.yyPos - P.yyPos;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    /**
     * Calculate the force between two planets using Newton’s law of universal gravitation.
     * @param P another planet instance.
     * @return the force in newton.
     */
    public double calcForceExertedBy(Planet P) {
        return gravitation * mass * P.mass / Math.pow(calcDistance(P), 2);
    }

    /**
     * Calculate the force in x coordinate exerted by another planet.
     * @param P another planet instance.
     * @return the force in newton.
     */
    public double calcForceExertedByX(Planet P) {
        double xDiff = P.xxPos - this.xxPos;
        return calcForceExertedBy(P) * xDiff / calcDistance(P);
    }

    /**
     * Calculate the force in y coordinate exerted by another planet.
     * @param P another planet instance.
     * @return the force in newton.
     */
    public double calcForceExertedByY(Planet P) {
        double yDiff = P.yyPos - this.yyPos;
        return calcForceExertedBy(P) * yDiff / calcDistance(P);
    }

    /**
     * Calculate the net force on this planet by an array of planets.
     * @param planets an array of planet objects.
     * @return the overall net force in x coordinate.
     */
    public double calcNetForceExertedByX(Planet[] planets) {
        double netForce = 0;
        for (Planet p : planets) {
            if (!this.equals(p)) {
                netForce += calcForceExertedByX(p);
            }
        }
        return netForce;
    }

    /**
     * Calculate the net force on this planet by an array of planets.
     * @param planets an array of planet objects.
     * @return the overall net force in y coordinate.
     */
    public double calcNetForceExertedByY(Planet[] planets) {
        double netForce = 0;
        for (Planet p : planets) {
            if (!this.equals(p)) {
                netForce += calcForceExertedByY(p);
            }
        }
        return netForce;
    }

    /**
     * Determines how much the forces exerted on the planet will cause it to accelerate.
     * Update position and velocity because of gravity in a given time interval.
     * @param dt a small period of time.
     * @param fX the net force in x coordinate.
     * @param fY the net force in y coordinate.
     */
    public void update(double dt, double fX, double fY) {
        double accelerationX = fX / mass;
        double accelerationY = fY / mass;
        xxVel += dt * accelerationX;
        yyVel += dt * accelerationY;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    /**
     * Draw the Planet’s image at its position.
     */
    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/"+imgFileName);
    }
}
