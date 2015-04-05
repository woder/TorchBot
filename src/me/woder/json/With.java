package me.woder.json;

import java.util.List;

public class With {
    private String translate;
    private String score;
    private String selector;
    private List<Object> extra;
    private String bold = "false";
    private String italic = "false";
    private String underlined = "false";
    private String strikethrough = "false";
    private String obfuscated = "false";
    private String color;
    private Clicked clickEvent;
    private Hover hoverEvent;
    private String insertion;
    private String text = "";
    
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getTranslate() {
        return translate;
    }
    public void setTranslate(String translate) {
        this.translate = translate;
    }
    public String getScore() {
        return score;
    }
    public void setScore(String score) {
        this.score = score;
    }
    public String getSelector() {
        return selector;
    }
    public void setSelector(String selector) {
        this.selector = selector;
    }
    public List<Object> getExtra() {
        return extra;
    }
    public void setExtra(List<Object> extra) {
        this.extra = extra;
    }
    public String getBold() {
        return bold;
    }
    public void setBold(String bold) {
        this.bold = bold;
    }
    public String getItalic() {
        return italic;
    }
    public void setItalic(String italic) {
        this.italic = italic;
    }
    public String getUnderlined() {
        return underlined;
    }
    public void setUnderlined(String underlined) {
        this.underlined = underlined;
    }
    public String getStrikethrough() {
        return strikethrough;
    }
    public void setStrikethrough(String strikethrough) {
        this.strikethrough = strikethrough;
    }
    public String getObfuscated() {
        return obfuscated;
    }
    public void setObfuscated(String obfuscated) {
        this.obfuscated = obfuscated;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public Clicked getClickEvent() {
        return clickEvent;
    }
    public void setClickEvent(Clicked clickEvent) {
        this.clickEvent = clickEvent;
    }
    public Hover getHoverEvent() {
        return hoverEvent;
    }
    public void setHoverEvent(Hover hoverEvent) {
        this.hoverEvent = hoverEvent;
    }
    public String getInsertion() {
        return insertion;
    }
    public void setInsertion(String insertion) {
        this.insertion = insertion;
    }
    public ChatMessage getNonNull(ChatMessage mes){
        if(this.text != null)mes.setText(this.text);
        if(this.translate != null)mes.setTranslate(this.translate);
        if(this.score != null)mes.setScore(this.score);
        if(this.selector != null)mes.setSelector(this.selector);
        if(this.extra != null)mes.setExtra(this.extra);
        if(this.bold != null)mes.setBold(this.bold);
        if(this.italic != null)mes.setItalic(this.italic);
        if(this.underlined != null)mes.setUnderlined(this.underlined);
        if(this.strikethrough != null)mes.setStrikethrough(this.strikethrough);
        if(this.obfuscated != null)mes.setObfuscated(this.obfuscated);
        if(this.color != null)mes.setColor(this.color);
        if(this.clickEvent != null)mes.setClickEvent(this.clickEvent);
        if(this.hoverEvent != null)mes.setHoverEvent(this.hoverEvent);
        if(this.insertion != null)mes.setInsertion(this.insertion);
        return mes;
    }
}
