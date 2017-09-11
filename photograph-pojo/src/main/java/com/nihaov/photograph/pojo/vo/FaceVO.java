package com.nihaov.photograph.pojo.vo;

/**
 * Created by nihao on 17/9/8.
 */
public class FaceVO {
    private String gender;
    private Double genderProbability;
    private Integer age;
    private String beauty;
    private String expression;
    private Double expressionProbablity;
    private String glasses;
    private Double glassesProbability;
    private String race;
    private Double raceProbability;
    private Double human;
    private Double cartoon;
    private Integer left;
    private Integer top;
    private Integer width;
    private Integer height;
    private Integer picW;
    private Integer picH;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getGenderProbability() {
        return genderProbability;
    }

    public void setGenderProbability(Double genderProbability) {
        this.genderProbability = genderProbability;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBeauty() {
        return beauty;
    }

    public void setBeauty(String beauty) {
        this.beauty = beauty;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Double getExpressionProbablity() {
        return expressionProbablity;
    }

    public void setExpressionProbablity(Double expressionProbablity) {
        this.expressionProbablity = expressionProbablity;
    }

    public String getGlasses() {
        return glasses;
    }

    public void setGlasses(String glasses) {
        this.glasses = glasses;
    }

    public Double getGlassesProbability() {
        return glassesProbability;
    }

    public void setGlassesProbability(Double glassesProbability) {
        this.glassesProbability = glassesProbability;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Double getRaceProbability() {
        return raceProbability;
    }

    public void setRaceProbability(Double raceProbability) {
        this.raceProbability = raceProbability;
    }

    public Double getHuman() {
        return human;
    }

    public void setHuman(Double human) {
        this.human = human;
    }

    public Double getCartoon() {
        return cartoon;
    }

    public void setCartoon(Double cartoon) {
        this.cartoon = cartoon;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getPicW() {
        return picW;
    }

    public FaceVO setPicW(Integer picW) {
        this.picW = picW;
        return this;
    }

    public Integer getPicH() {
        return picH;
    }

    public FaceVO setPicH(Integer picH) {
        this.picH = picH;
        return this;
    }
}
