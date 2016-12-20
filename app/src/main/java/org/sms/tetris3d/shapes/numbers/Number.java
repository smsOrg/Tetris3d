package org.sms.tetris3d.shapes.numbers;

import com.trippleit.android.tetris3d.shapes.AbstractDraw;
import com.trippleit.android.tetris3d.shapes.IShape;

import org.sms.tetris3d.players.User;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by hsh on 2016. 11. 20..
 */

/**
 * opengl에서 텍스쳐를 이용한 렌더링대신 도형으로 도트개념을 응요한 10미만의 숫자들
 *
 * @version 1.3
 *
 * @author 황세현
 *
 */
public class Number extends AbstractDraw implements IShape {
    String color = "#FF7400";
    User usr;
    boolean objectMatrix[][][];

    /**
     * 10미만의 숫자를 가져와 도형 매트릭스를 채우고 설정함
     * @param user
     * @param num
     */
    public Number(User user,int num) {

        switch (num) {
            case 0: {
                objectMatrix = createFalsMatrix(4);
                for (int i = 0; i < 3; i++) {
                    if (i == 1) {
                        for (int j = 0; j <= 1; j++) {
                            objectMatrix[i][0][3 * j] = true;
                        }
                    } else
                        for (int j = 0; j < 4; j++) {
                            objectMatrix[i][0][j] = true;
                        }
                }
                break;
            }
            case 1:{
                objectMatrix = createFalsMatrix(6);
                for(int i =0;i <3;i++){
                    objectMatrix[i][0][0] = true;
                }
                for(int i=1;i<=4;i++) {
                    objectMatrix[1][0][i] = true;
                }
                objectMatrix[0][0][3] = true;

                break;
            }
            case 2:{
                objectMatrix = createFalsMatrix(5);
                for(int i =0;i<objectMatrix.length;i+=2){
                    for(int j =0;j<3;j++){
                        objectMatrix[j][0][i]=true;
                    }

                }
                objectMatrix[0][0][1]=true;
                objectMatrix[2][0][3]=true;
                break;
            }
            case 3:{
                objectMatrix = createFalsMatrix(5);
                for(int i =0;i<objectMatrix.length;i+=2){
                    for(int j =0;j<3;j++){
                        objectMatrix[j][0][i]=true;
                    }

                }
                for(int i =1;i<=3;i++) {
                        objectMatrix[2][0][i] = i%2==1;
                }
                break;
            }
            case 4:{
                objectMatrix = createFalsMatrix(5);
                for(int i =0;i<objectMatrix.length;i++){
                        objectMatrix[i][0][2]=true;
                }
                for(int i =0;i<objectMatrix.length;i++){
                    objectMatrix[2][0][i]=true;
                }
                for(int i = 3;i<5;i++) {
                    objectMatrix[0][0][i] = true;
                }
                break;
            }
            case 5:{
                objectMatrix = createFalsMatrix(5);
                for(int i =0;i<objectMatrix.length;i+=2){
                    for(int j =0;j<3;j++){
                        objectMatrix[j][0][i]=true;
                    }

                }
                objectMatrix[2][0][1]=true;
                objectMatrix[0][0][3]=true;
                break;
            }
            case 6:{
                objectMatrix = createFalsMatrix(5);
                for(int i =0;i<objectMatrix.length;i+=2){
                    for(int j =0;j<3;j++){
                        objectMatrix[j][0][i]=true;
                    }

                }
                for(int i =0;i<3;i+=2){
                    for(int j =1;j<=3;j+=2){
                        objectMatrix[i][0][j]=true;
                    }
                }
                objectMatrix[2][0][3]=false;

                break;
            }
            case 7:{
                objectMatrix = createFalsMatrix(5);
                for(int i =0;i<5;i++){
                    objectMatrix[4][0][i]=true;
                }
                for(int i =0;i<4;i++){
                    objectMatrix[i][0][4]=true;
                }
                objectMatrix[0][0][3]=true;
                break;
            }
            case 8:{
                objectMatrix = createFalsMatrix(5);
                for(int i =0;i<objectMatrix.length;i+=2){
                    for(int j =0;j<3;j++){
                        objectMatrix[j][0][i]=true;
                    }

                }
                for(int i =0;i<3;i+=2){
                    for(int j =1;j<=3;j+=2){
                        objectMatrix[i][0][j]=true;
                    }
                }

                break;
            }
            case 9:{
                objectMatrix = createFalsMatrix(5);
                for(int i =0;i<objectMatrix.length;i+=2){
                    for(int j =0;j<3;j++){
                        objectMatrix[j][0][i]=true;
                    }

                }
                for(int i =0;i<3;i+=2){
                    for(int j =1;j<=3;j+=2){
                        objectMatrix[i][0][j]=true;
                    }
                }
                objectMatrix[0][0][1] = false;
                break;
            }


            default:break;
        }
        setUser(user);
    }
    @Override
    public User getUser() {
        return usr;
    }

    @Override
    public void setUser(User usr) {
        this.usr=usr;
    }

    @Override
    public void draw(GL10 gl) {
        drawObject(gl, objectMatrix, color);
    }

    @Override
    public void drawLineBone(GL10 gl) {

    }

    @Override
    public boolean[][][] getObjectMatrix() {
        return objectMatrix;
    }
    @Override
    public String getColor() {
        return color;
    }

    @Override
    public int getXsize() {
        return getXsize(objectMatrix);
    }

    @Override
    public int getYsize() {
        return getYsize(objectMatrix);
    }

    @Override
    public int getZsize() {
        return getZsize(objectMatrix);
    }

    @Override
    public void rotate(int axis) {

    }
}
