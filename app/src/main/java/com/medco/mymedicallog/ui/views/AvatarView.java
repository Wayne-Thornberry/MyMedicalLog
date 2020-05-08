package com.medco.mymedicallog.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.medco.mymedicallog.R;
import com.medco.mymedicallog.ui.interfaces.OnAvatarInteractionListener;
import com.otaliastudios.zoom.ZoomLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AvatarView extends CoordinatorLayout {
    public static final int CONTROL_LEFT = 0;
    public static final int CONTROL_RIGHT = 1;

    // Display stuff
    private View[] mGridCellViews;
    private FloatingActionButton mFabLeft;
    private FloatingActionButton mFabRight;
    private ZoomLayout mZoomLayout;
    private RelativeLayout mRelativeLayout;
    private ImageView mImageViewPrimary;
    private ImageView mImageViewMask;
    private GridLayout mGridLayout;
    private ArrayList<ArrayMap<String,Integer[]>> mPresets;

    // Logical stuff
    private OnAvatarInteractionListener mListener;
    private int[] mAvatarImages;
    private int[] mAvatarMasks;
    private int mAvatarView;
    private boolean mSelectable, mMultiSelectable;

    private List<String> mHighlightTags;
    private List<String> mSelectedTags;

    public AvatarView(Context context)
    {
        super(context);
        init();

    }

    public AvatarView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.AvatarView, 0, 0);
        try {
            mSelectable = a.getBoolean(R.styleable.AvatarView_enable_selectable, false);
            mMultiSelectable = a.getBoolean(R.styleable.AvatarView_enable_multi_selectable, false);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        mZoomLayout = new ZoomLayout(this.getContext());
        mZoomLayout.setHasClickableChildren(true);
        mRelativeLayout = new RelativeLayout(this.getContext());
        mImageViewPrimary = new ImageView(this.getContext());
        mImageViewMask= new ImageView(this.getContext());
        mGridLayout = new GridLayout(this.getContext());
        mGridCellViews = new View[64];
        mGridLayout.setColumnCount(8);
        mGridLayout.setOrientation(GridLayout.HORIZONTAL);
        mGridLayout.setRowCount(8);
        for (int i = 0; i < mGridCellViews.length; i++) {
            View view = new TextView(this.getContext());
            GridLayout.LayoutParams parms = new GridLayout.LayoutParams();
            parms.setGravity(Gravity.FILL);
            parms.columnSpec = GridLayout.spec(GridLayout.UNDEFINED,1f);
            parms.rowSpec = GridLayout.spec(GridLayout.UNDEFINED,1f);
            view.setLayoutParams(parms);
            mGridCellViews[i] = view;
            mGridLayout.addView(mGridCellViews[i]);
        }

        mSelectedTags = new ArrayList<>();
        mHighlightTags = new ArrayList<>();

        mFabLeft = new FloatingActionButton(this.getContext());
        mFabRight = new FloatingActionButton(this.getContext());
        mFabLeft.setElevation(6);
        mFabRight.setElevation(6);
        mFabRight.setCompatPressedTranslationZ(12);
        mFabLeft.setCompatPressedTranslationZ(12);
        mFabLeft.setImageDrawable(getResources().getDrawable(R.drawable.ic_chevron_left));
        mFabRight.setImageDrawable(getResources().getDrawable(R.drawable.ic_chevron_right));

        CoordinatorLayout.LayoutParams d = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.setMargins((int) getResources().getDimension(R.dimen.fab_margin),(int) getResources().getDimension(R.dimen.fab_margin),(int) getResources().getDimension(R.dimen.fab_margin),(int) getResources().getDimension(R.dimen.fab_margin));
        d.gravity = (Gravity.CENTER_VERTICAL | Gravity.START);
        mFabLeft.setLayoutParams(d);

        CoordinatorLayout.LayoutParams e = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        e.setMargins((int) getResources().getDimension(R.dimen.fab_margin),(int) getResources().getDimension(R.dimen.fab_margin),(int) getResources().getDimension(R.dimen.fab_margin),(int) getResources().getDimension(R.dimen.fab_margin));
        e.gravity = (Gravity.CENTER_VERTICAL | Gravity.END);
        mFabRight.setLayoutParams(e);

        mRelativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(350,450));


        mAvatarImages = new int[]{
                R.drawable.ic_head_front,
                R.drawable.ic_head_side,
                R.drawable.ic_head_back,
                R.drawable.ic_head_side,
        };

        mAvatarMasks = new int[]{
                R.drawable.ic_head_front_outline,
                R.drawable.ic_head_side_outline,
                R.drawable.ic_head_back_outline,
                R.drawable.ic_head_side_outline,
        };

        mPresets = new ArrayList<>();
        ArrayMap<String, Integer[]> headFrontPreset = new ArrayMap<>();
        headFrontPreset.put("HEAD_RIGHT_FRONT_CROWN",  new Integer[] {0,1,8,9});
        headFrontPreset.put("HEAD_CENTER_FRONT_CROWN", new Integer[] {2,3,4,5,10,11,12,13});
        headFrontPreset.put("HEAD_LEFT_FRONT_CROWN", new Integer[] {6,7,14,15});

        headFrontPreset.put("HEAD_RIGHT_TEMPLE", new Integer[] {16});
        headFrontPreset.put("HEAD_RIGHT_BROW", new Integer[] {17,18,19});
        headFrontPreset.put("HEAD_LEFT_BROW", new Integer[] {20,21,22});
        headFrontPreset.put("HEAD_LEFT_TEMPLE", new Integer[] {23});
        headFrontPreset.put("HEAD_RIGHT_EAR", new Integer[] {24,32});
        headFrontPreset.put("HEAD_RIGHT_EYE", new Integer[] {25,26});
        headFrontPreset.put("HEAD_NOSE", new Integer[] {27,28,35,36});
        headFrontPreset.put("HEAD_LEFT_EYE", new Integer[] {29,30});
        headFrontPreset.put("HEAD_LEFT_EAR", new Integer[] {31,39});
        headFrontPreset.put("HEAD_RIGHT_CHECK", new Integer[] {33,34,41,42});
        headFrontPreset.put("HEAD_LEFT_CHECK", new Integer[] {37,38,45,46});
        headFrontPreset.put("HEAD_MOUTH", new Integer[] {43,44});

        headFrontPreset.put("HEAD_CHIN", new Integer[] {50,51,52,53});
        headFrontPreset.put("HEAD_RIGHT_FRONT_NECK", new Integer[] {49,57});
        headFrontPreset.put("HEAD_FRONT_NECK", new Integer[] {58,59,60,61});
        headFrontPreset.put("HEAD_LEFT_FRONT_NECK", new Integer[] {62,54});


        ArrayMap<String, Integer[]> headSide1Preset = new ArrayMap<>();
        headSide1Preset.put("HEAD_LEFT_FRONT_CROWN",  new Integer[] {0,1,8,9});
        headSide1Preset.put("HEAD_LEFT_SIDE_CROWN", new Integer[] {2,3,4,5,10,11,12,13});
        headSide1Preset.put("HEAD_LEFT_BACK_CROWN", new Integer[] {6,7,14,15,22,23});

        headSide1Preset.put("HEAD_LEFT_BROW", new Integer[] {16,17,18});
        headSide1Preset.put("HEAD_LEFT_TEMPLE", new Integer[] {19,20,21,27,28});
        headSide1Preset.put("HEAD_LEFT_EYE", new Integer[] {25,26});
        headSide1Preset.put("HEAD_NOSE", new Integer[] {24,32,33});
        headSide1Preset.put("HEAD_MOUTH", new Integer[] {40,41});
        headSide1Preset.put("HEAD_LEFT_EAR", new Integer[] {29,30,37,38});
        headSide1Preset.put("HEAD_LEFT_BACK", new Integer[] {31,39,47});
        headSide1Preset.put("HEAD_LEFT_CHECK", new Integer[] {42,34,35,36,43,44});
        headSide1Preset.put("HEAD_CHIN", new Integer[] {48,49,50,51});

        headSide1Preset.put("HEAD_LEFT_FRONT_NECK", new Integer[] {57,58});
        headSide1Preset.put("HEAD_LEFT_SIDE_NECK", new Integer[] {45,52,53,59,60,61});
        headSide1Preset.put("HEAD_LEFT_BACK_NECK", new Integer[] {46,54,62,47,55,63});

        ArrayMap<String, Integer[]> headBackPreset = new ArrayMap<>();

        headBackPreset.put("HEAD_LEFT_BACK_CROWN",  new Integer[] {0,1,8,9,16,17});
        headBackPreset.put("HEAD_CENTER_BACK_CROWN", new Integer[] {2,3,4,5,10,11,12,13,18,19,20,21});
        headBackPreset.put("HEAD_RIGHT_BACK_CROWN", new Integer[] {6,7,14,15,22,23});

        headBackPreset.put("HEAD_LEFT_BACK", new Integer[] {25,26,27,33,34,35});
        headBackPreset.put("HEAD_LEFT_EAR", new Integer[] {24,32});
        headBackPreset.put("HEAD_RIGHT_EAR", new Integer[] {31,39});
        headBackPreset.put("HEAD_RIGHT_BACK", new Integer[] {28,29,30,36,37,38});

        headBackPreset.put("HEAD_BACK_NECK", new Integer[] {42,43,44,45,50,51,52,53,58,59,60,61});
        headBackPreset.put("HEAD_LEFT_BACK_NECK", new Integer[] {41,49,57});
        headBackPreset.put("HEAD_RIGHT_BACK_NECK", new Integer[] {46,54,62});


        ArrayMap<String, Integer[]> headSide2Preset = new ArrayMap<>();

        headSide2Preset.put("HEAD_RIGHT_FRONT_CROWN", new Integer[] {6,7,14,15});
        headSide2Preset.put("HEAD_RIGHT_SIDE_CROWN", new Integer[] {2,3,4,5,10,11,12,13});
        headSide2Preset.put("HEAD_RIGHT_BACK_CROWN",  new Integer[] {0,1,8,9,16,17});

        headSide2Preset.put("HEAD_RIGHT_BROW", new Integer[] {21,22,23});
        headSide2Preset.put("HEAD_RIGHT_TEMPLE", new Integer[] {18,19,20,27,28});
        headSide2Preset.put("HEAD_RIGHT_EYE", new Integer[] {29,30});
        headSide2Preset.put("HEAD_NOSE", new Integer[] {31,39,38});
        headSide2Preset.put("HEAD_MOUTH", new Integer[] {46,47});
        headSide2Preset.put("HEAD_RIGHT_EAR", new Integer[] {25,26,33,34});
        headSide2Preset.put("HEAD_RIGHT_BACK", new Integer[] {24,32});
        headSide2Preset.put("HEAD_RIGHT_CHECK", new Integer[] {35,36,37,43,44,45});
        headSide2Preset.put("HEAD_CHIN", new Integer[] {55,54,53,52});

        headSide2Preset.put("HEAD_RIGHT_FRONT_NECK", new Integer[] {61,62});
        headSide2Preset.put("HEAD_RIGHT_SIDE_NECK", new Integer[] {42,50,51,58,59,60});
        headSide2Preset.put("HEAD_RIGHT_BACK_NECK", new Integer[] {40,41,48,49,56,57});

        mPresets.add(headFrontPreset);
        mPresets.add(headSide1Preset);
        mPresets.add(headBackPreset);
        mPresets.add(headSide2Preset);

        mZoomLayout.addView(mRelativeLayout);
        mRelativeLayout.addView(mImageViewPrimary);
        mRelativeLayout.addView(mGridLayout);
        mRelativeLayout.addView(mImageViewMask);


        mFabLeft.setOnClickListener(c->{
            setView(mAvatarView + 1);
        });
        mFabRight.setOnClickListener(c->{
            setView(mAvatarView - 1);
        });

        for (int i = 0; i < mGridCellViews.length; i++) {
            View v = mGridCellViews[i];
            v.setAlpha(0.2f);
            if(mSelectable) {
                mGridCellViews[i].setOnClickListener(f -> {
                    if(mListener != null)
                        selectTag(v.getTag().toString());
                });
            }
        }

        this.addView(mZoomLayout);
        this.addView(mFabLeft);
        this.addView(mFabRight);
    }

    public void setOnAvatarInteraction(OnAvatarInteractionListener listener){
        mListener = listener;
    }

    public void tagCells(String tag, Integer[] cells){
        for (int i = 0; i < cells.length; i++) {
            mGridCellViews[cells[i]].setTag(tag);
        }
    }

    public void selectTag(String tag){

        if(!mSelectedTags.contains(tag)) {
            if (!mMultiSelectable && mSelectedTags.size() > 0) {
                deselectTag(mSelectedTags.get(0));
            }
            colorTag(tag, getResources().getColor(R.color.color_theme_primary));
            mSelectedTags.add(tag);
            mListener.onAvatarCellInteraction(tag, true);
        }
        else{
            deselectTag(tag);
            mListener.onAvatarCellInteraction(tag, false);
        }
    }

    public void deselectTag(String tag){
        colorTag(tag, 0);
        if(mHighlightTags.contains(tag)){
            colorTag(tag, getResources().getColor(R.color.color_status_warning));
        }

        if(mSelectedTags.contains(tag))
            mSelectedTags.remove(tag);
    }

    public void colorTag(String tag, int color){
        for (int i = 0; i < mGridCellViews.length; i++) {
            if(mGridCellViews[i].getTag() == null) continue;
            if(mGridCellViews[i].getTag().equals(tag)){
                highlightCell(mGridCellViews[i], color);
            }
        }
    }

    public void highlightCell(View v, int color){
        v.setBackgroundColor(color);
    }

    public int getView(){
        return mAvatarView;
    }

    public void setView(int view) {
        if(view < 0){
            view = 3;
        }else if(view > 3){
            view = 0;
        }
        mAvatarView = view;
        load();
    }

    private void load() {
        resetCells();
        Drawable layer1 = getResources().getDrawable(mAvatarImages[mAvatarView]);
        Drawable layer2 = getResources().getDrawable(mAvatarMasks[mAvatarView]);
        layer2.setTint(getResources().getColor(R.color.color_white));
        if(mAvatarView == 3) {
            mImageViewPrimary.setScaleX(-1);
            mImageViewMask.setScaleX(-1);
        }else{
            mImageViewPrimary.setScaleX(1);
            mImageViewMask.setScaleX(1);
        }
        mImageViewPrimary.setBackground(layer1);
        mImageViewMask.setBackground(layer2);

        mGridLayout.getLayoutParams().width =  ViewGroup.LayoutParams.MATCH_PARENT;
        mGridLayout.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        mGridLayout.requestLayout();
        loadPreset();
        loadSelects();
        loadHighlights();
    }


    private void resetCells() {
        for (View c : mGridCellViews){
            c.setTag("");
            c.setBackgroundColor(0);
        }
    }


    private void loadPreset(){
        ArrayMap<String, Integer[]> preset = mPresets.get(mAvatarView);
        for (Map.Entry<String, Integer[]> d : preset.entrySet()){
            tagCells(d.getKey(), d.getValue());
        }
    }

    private void loadSelects() {
        for (String tag : mSelectedTags){
            colorTag(tag,  getResources().getColor(R.color.color_theme_primary));
        }
    }

    private void loadHighlights() {
        for (String tag : mHighlightTags){
            colorTag(tag,  getResources().getColor(R.color.color_status_warning));
        }
    }


    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
    }

    public void setHighlightZones(List<String> highlightZones) {
        mHighlightTags = highlightZones;
    }

    public void setSelectedTags(List<String> selectedTags){
        mSelectedTags = selectedTags;
    }
}
