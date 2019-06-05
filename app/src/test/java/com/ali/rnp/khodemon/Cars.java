package com.ali.rnp.khodemon;


public abstract class Cars {
    public abstract float power();
    public abstract float fuel();
    public abstract float speed();
}

abstract class iranKhodro extends Cars{

    @Override
    public float speed() {
        return 120;
    }

    @Override
    public float fuel() {
        return 200;
    }
}

class samand extends iranKhodro{
    @Override
    public float power() {
        return 0;
    }

    @Override
    public float fuel() {
        return super.fuel();
    }

    @Override
    public float speed() {
        return super.speed();
    }
}

