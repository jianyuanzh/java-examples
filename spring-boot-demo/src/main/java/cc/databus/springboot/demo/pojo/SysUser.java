package cc.databus.springboot.demo.pojo;

import javax.persistence.*;

@Table(name = "sys_user")
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    private Integer age;

    /**
     * 性别
     * 0：女
     * 1：男
     * 2：保密
     */
    private Integer sex;

    /**
     * 职业类型：
     * 1. Java 研发
     * 2. 前段开发
     * 3. 大数据开发
     * 4. ios
     * 5. android
     * 6. linux系统
     */
    private Integer job;

    /**
     * 头像地址
     */
    @Column(name = "face_image")
    private String faceImage;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取昵称
     *
     * @return nickname - 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取性别
     * 0：女
     * 1：男
     * 2：保密
     *
     * @return sex - 性别
     * 0：女
     * 1：男
     * 2：保密
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别
     * 0：女
     * 1：男
     * 2：保密
     *
     * @param sex 性别
     *            0：女
     *            1：男
     *            2：保密
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取职业类型：
     * 1. Java 研发
     * 2. 前段开发
     * 3. 大数据开发
     * 4. ios
     * 5. android
     * 6. linux系统
     *
     * @return job - 职业类型：
     * 1. Java 研发
     * 2. 前段开发
     * 3. 大数据开发
     * 4. ios
     * 5. android
     * 6. linux系统
     */
    public Integer getJob() {
        return job;
    }

    /**
     * 设置职业类型：
     * 1. Java 研发
     * 2. 前段开发
     * 3. 大数据开发
     * 4. ios
     * 5. android
     * 6. linux系统
     *
     * @param job 职业类型：
     *            1. Java 研发
     *            2. 前段开发
     *            3. 大数据开发
     *            4. ios
     *            5. android
     *            6. linux系统
     */
    public void setJob(Integer job) {
        this.job = job;
    }

    /**
     * 获取头像地址
     *
     * @return face_image - 头像地址
     */
    public String getFaceImage() {
        return faceImage;
    }

    /**
     * 设置头像地址
     *
     * @param faceImage 头像地址
     */
    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }
}