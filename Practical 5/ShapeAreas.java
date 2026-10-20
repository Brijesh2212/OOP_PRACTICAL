abstract class Shape {
    abstract double area();
}

class Circle extends Shape {
    double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    public double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    double length, width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    public double area() {
        return length * width;
    }
}

class Triangle extends Shape {
    double base, height;

    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    public double area() {
        return 0.5 * base * height;
    }
}

public class ShapeAreas {
    public static void main(String[] args) {
        Shape[] shapes = {
            new Circle(5),
            new Rectangle(4, 6),
            new Triangle(8, 3)
        };

        double total = 0;
        double largest = 0;

        for (Shape shape : shapes) {
            double area = shape.area();

            System.out.println(shape.getClass().getSimpleName() + " Area = " + area );

            total += area;

            if (area > largest) {
                largest = area;
            }
        }

        System.out.println("Total Area = " + total);
        System.out.println("Largest Area = " + largest);
    }
}

