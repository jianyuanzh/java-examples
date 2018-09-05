package cc.databus.aspect.intertype;

public aspect PointAspect {
    // creates a new interface named HasName
    private interface HasName{}
    // make class Ppint implements HashName
    declare parents: Point implements HasName;
    // make HasName has a field named name
    private String HasName.name;
    // make HasName has a method getName() and default implemented
    public String HasName.getName() {
        return name;
    }

    // make HasName has a method named setName and default
    public void HasName.setName(String name) {
        this.name = name;
    }

    // add a field named created to class Point
    // with default value 0
    long Point.created = 0;


    // add a field named lastUpdated to class Point
    // with default value 0
    private long Point.lastUpdated = 0;


    // add a private method setUpdated()
    private void Point.setUpdated() {
        this.lastUpdated = System.currentTimeMillis();
    }

    // implement toString() for Point
    // include the fields added in the aspect file
    public String Point.toString() {
        return String.format(
                "Point: {name=%s, x=%d; y=%d, created=%d, updated=%d}",
                getName(), getX(), getY(), created, lastUpdated);
    }

    // pointcut the constructor, and set the value for created
    after() returning(Point p) : call(Point.new(..)) && !within(PointAspect) {
        System.out.println(thisJoinPointStaticPart);
        System.out.println("Set created");
        p.created = System.currentTimeMillis();
    }

    // define a pointcut for setX and setY
    pointcut update(Point p): target(p) && call(void Point.set*(..));

    // make the lastUpdated updated every time
    // setX or setY invoked
    after(Point p): update(p) && !within(PointAspect) {
        System.out.println("set updated for Point due to " + thisJoinPointStaticPart);
        p.setUpdated();
    }
}
