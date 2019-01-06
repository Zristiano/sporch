package sporch.hexagonblock.data;

public enum UserPlatform {
    Native,
    QQ,
    Wechat,
    Weibo,
    Facebook,
    Twitter,
    Google;

    /**
     * @return number of defined platform, it must be changed along with addition/deletion
     * of platforms listed above
     */
    public static int getCount(){
        return 7;
    }
}
