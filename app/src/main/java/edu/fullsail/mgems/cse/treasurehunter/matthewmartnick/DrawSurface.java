package edu.fullsail.mgems.cse.treasurehunter.matthewmartnick;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;


public class DrawSurface extends SurfaceView implements View.OnTouchListener {


    private SurfaceHolder surfaceHolder;
    private Bitmap mBMPField;
    private Bitmap mBMPHole;

    static final ArrayList<Item> found = new ArrayList<Item>();
    ArrayList<Float> dataX = new ArrayList<>();
    ArrayList<Float> dataY = new ArrayList<>();
    ArrayList<Item> mItems = loadItems();

    public ArrayList<Item> loadItems() {
        InputStream input = getResources().openRawResource(R.raw.items);
        BufferedReader reader = null;
        ArrayList<Item> items = new ArrayList<>();
        String line;

        try {
            reader = new BufferedReader(new InputStreamReader(input));
            while ((line = reader.readLine()) != null) {
                items.add(new Item(line));

                System.out.println("Hello Matt" + items);
            }
        } catch (Exception e) {
            Log.e("MainActivity", "Reading list of Items failed!", e);
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (Exception e) {
                Log.e("MainActivity", "Error closing file reader.", e);
            }
        }
        //System.out.println("Hello Matt" + items);

        for (int i = 0; i < items.size(); i++) {
            System.out.println("Name " + items.get(i).toString());
        }

        return items;
    }

    public DrawSurface(Context context) {
        super(context);
        init();
    }

    public DrawSurface(Context context,
                       AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawSurface(Context context,
                       AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        surfaceHolder = getHolder();
        mBMPField = BitmapFactory.decodeResource(getResources(),
                R.drawable.field);
        mBMPHole = BitmapFactory.decodeResource(getResources(), R.drawable.hole);
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                Canvas c = holder.lockCanvas();

                Rect mFieldDim = new Rect();
                if (c != null) mFieldDim.set(0, 0, c.getWidth(), c.getHeight());
                invalidate();
                System.out.println("Yo Matt" + mItems);
                for (int i = 0; i < mItems.size(); i++) {
                    mItems.get(i).x = (int) (Math.random() * (float) mFieldDim.width());
                    mItems.get(i).y = (int) (Math.random() * (float) mFieldDim.height());
                }

                drawField(c);
                holder.unlockCanvasAndPost(c);
                invalidate();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub

            }
        });

        findViewById(R.id.dsField);
        setOnTouchListener(this);
    }

    protected void drawField(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mBMPField, 0, 0, null);
    }

    protected void drawHole(Canvas canvas, float X, float Y) {

        canvas.drawBitmap(mBMPHole, X, Y, null);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            float X = event.getX();
            float Y = event.getY();

            dataX.add(X);
            dataY.add(Y);

            if (surfaceHolder.getSurface().isValid()) {
                Canvas canvas = surfaceHolder.lockCanvas();
                canvas.drawBitmap(mBMPField, 0, 0, null);

                for (int i = 0; i < dataX.size(); i++) {
                    drawHole(canvas, dataX.get(i), dataY.get(i));
                    System.out.println(dataX);
                }
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

            int radius = Math.max(mBMPHole.getHeight(), mBMPHole.getWidth()) / 2;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                for (int i = mItems.size() - 1; i > 0; i--) {
                    float dx = mItems.get(i).x - X;
                    float dy = mItems.get(i).y - Y;

                    if ((dx) * (dx) + (dy) * (dy) < radius * radius) {

                        found.add(mItems.get(i));

                        Toast.makeText(getContext(), mItems.get(i).name, LENGTH_SHORT).show();

                        mItems.remove(i);
                        System.out.println("Bye " + mItems);
                    }

                }
            }
        }

        setWillNotDraw(false);
        return true;
    }

    public static ArrayList<Item> getInventory() {


        for (int i = 0; i < found.size(); i++) {
            System.out.println("fish " + found.get(i).name);

        }
        return found ;
    }
}