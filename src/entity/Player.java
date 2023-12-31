package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    private final GamePanel gp;
    private final KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        this.screenX = (gp.screenWidth / 2) - (gp.tileSize / 2);
        this.screenY = (gp.screenHeight / 2) - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        try {
            up1 = ImageIO.read(Objects.requireNonNull
                    (getClass().getClassLoader().getResourceAsStream("player/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull
                    (getClass().getClassLoader().getResourceAsStream("player/boy_up_2.png")));

            down1 = ImageIO.read(Objects.requireNonNull
                    (getClass().getClassLoader().getResourceAsStream("player/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull
                    (getClass().getClassLoader().getResourceAsStream("player/boy_down_2.png")));

            right1 = ImageIO.read(Objects.requireNonNull
                    (getClass().getClassLoader().getResourceAsStream("player/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull
                    (getClass().getClassLoader().getResourceAsStream("player/boy_right_2.png")));


            left1 = ImageIO.read(Objects.requireNonNull
                    (getClass().getClassLoader().getResourceAsStream("player/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull
                    (getClass().getClassLoader().getResourceAsStream("player/boy_left_2.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update() {

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // IF COLLISION == FALSE, PLAYER CAN MOVE
            if (!collisionOn) {

                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            getSpriteNumber();

        }

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNumber == 1) {
                    image = up1;
                } else if (spriteNumber == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNumber == 1) {
                    image = down1;
                } else if (spriteNumber == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNumber == 1) {
                    image = left1;
                } else if (spriteNumber == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNumber == 1) {
                    image = right1;
                } else if (spriteNumber == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }

    private void getSpriteNumber() {

        if (spriteCounter > 12) {
            if (spriteNumber == 1){
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }
}
