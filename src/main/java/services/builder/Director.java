package services.builder;

public abstract class Director {

    protected Builder builder;

    public void build(){
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
    }

}
