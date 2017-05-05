package me.woder.bot;

public enum MobTypes {
    
    ARMORSTAND(30, "ArmorStand", false),
    MOB(48, "Mob", false),
    MONSTER(48, "Monster", true),
    CREEPER(50, "Creeper", true),
    SKELETON(51, "Skeleton", true),
    SPIDER(52, "Spider", true),
    GIANTZOMBIE(53, "GiantZombie", true),
    ZOMBIE(54, "Zombie", true),
    SLIME(55, "Slime", true),
    GHAST(56, "Ghast", true),
    ZOMBIEPIGMAN(57, "ZombiePigman", false),
    ENDERMAN(58, "Enderman", true),
    CAVESPIDER(59, "CaveSpider", true),
    SILVERFISH(60, "Silverfish", true),
    BLAZE(61, "Blaze", true),
    MAGMACUBE(62, "MagmaCube", true),
    ENDERDRAGON(63, "EnderDragon", true),
    WITHER(64, "Wither", true),
    BAT(65, "Bat", true),
    WITCH(66, "Witch", true),
    ENDERMITE(67, "Endermite", true),
    GUARDIAN(68, "Guardian", false),
    PIG(90, "Pig", false),
    SHEEP(91, "Sheep", false),
    COW(92, "Cow", false),
    CHICKEN(93, "Chicken", false),
    SQUID(94, "Squid", false),
    WOLF(95, "Wolf", false),
    MOOSHROOM(96, "Mooshroom", false),
    SNOWMAN(97, "Snowman", false),
    OCELOT(98, "Ocelot", false),
    IRONGOLEM(99, "IronGolem", false),
    HORSE(100, "Horse", false),
    RABBIT(101, "Rabbit", false),
    VILLAGER(120, "Villager", false),
    DEFAULT(99999, "Unknown", false);
    
    private final int type;
    private final String name;
    private final boolean hostile;
    
    private MobTypes(int type, String name, boolean hostile){
        this.type = type;
        this.name = name;
        this.hostile = hostile;
    }
    
    public String getFullName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public boolean isHostile(){
        return hostile;
    }
    
    public static MobTypes findByType(int type){
        for(MobTypes v : values()){
            if( v.type == type){
                return v;
            }
        }
        return DEFAULT; //return dummy for when there is no value
    }
    
    public static MobTypes findByName(String name){
        for(MobTypes v : values()){
            if( v.name.equalsIgnoreCase(name)){
                return v;
            }
        }
        return DEFAULT;
    }
}
