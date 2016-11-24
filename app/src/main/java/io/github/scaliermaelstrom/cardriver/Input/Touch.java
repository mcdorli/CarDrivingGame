package io.github.scaliermaelstrom.cardriver.Input;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import io.github.scaliermaelstrom.cardriver.math.Vector2;

public class Touch implements View.OnTouchListener {

    private ArrayList<Vector2> positions = new ArrayList<>();

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int index = event.getActionIndex();
        Vector2 position = new Vector2(
                event.getX() / v.getMeasuredWidth() - 0.5f,
                event.getY() / v.getMeasuredHeight() - 0.5f
        );
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                positions.add(index, position);
                break;
            case MotionEvent.ACTION_MOVE:
                if (positions.size() > 0 && index != -1)
                    positions.set(index, position);
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                if (positions.size() > 0 && index != -1)
                    positions.remove(index);
                break;
        }

        return true;
    }

    public ArrayList<Vector2> getPositions() {
        return positions;
    }
}
