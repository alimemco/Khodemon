package com.ali.rnp.khodemon.Builder;

public class BuilderTest {
    private String name;
    private String family;
    private int age;

    public BuilderTest(PersonnelBuilder personnelBuilder){
        this.name = personnelBuilder.name;
        this.family = personnelBuilder.family;
        this.age = personnelBuilder.age;
    }


    public static class PersonnelBuilder {

        private String name;
        private String family;
        private int age;

        public PersonnelBuilder(){

        }

        public PersonnelBuilder setName(String name){
            this.name = name;
            return this;
        }

        public PersonnelBuilder setFamily(String family){
            this.family = family;
            return this;
        }


        public PersonnelBuilder setAge(int age){
            this.age = age;
            return this;
        }

        public BuilderTest build(){
            return new BuilderTest(this);
        }

    }

}
