package com.openatk.field_work.views;

import android.graphics.Color;
import android.util.Log;

import com.openatk.field_work.models.BaseField;
import com.openatk.openatklib.atkmap.ATKMap;
import com.openatk.openatklib.atkmap.models.ATKPolygon;
import com.openatk.openatklib.atkmap.views.ATKPolygonView;

/**
 * Created by ndrs on 7/6/2016.
 */
public class BaseFieldView {


    public static float STROKE_WIDTH = 5.0f;
    public static int STROKE_COLOR_NORMAL = Color.RED;
    public static int STROKE_COLOR_SELECTED = Color.WHITE;


    public static int LABEL_COLOR_NORMAL = Color.BLACK;
    public static int LABEL_COLOR_SELECTED = Color.WHITE;

    public static int FILL_COLOR_NOT_PLANNED = Color.argb(128, 186, 188, 190);

    public static int FILL_COLOR_NOT_BASEWORKER=Color.argb(128, 186, 188, 190);

    public static int FILL_COLOR_NOT_SELECTED=Color.DKGRAY;
    public static int FILL_COLOR_SELECTED=Color.TRANSPARENT;

    public static int STATE_NORMAL = 0;
    public static int STATE_SELECTED = 1;
    public static int STATE_EDITING = 2;

    private int state = STATE_NORMAL;

    private BaseField base;


    private ATKPolygonView polygonView;
    private ATKMap map;

    public BaseFieldView(BaseField base,ATKMap map){
        this(STATE_NORMAL,base, map);
    }
    public BaseFieldView(int state, BaseField base, ATKMap map) {
        this(state,base, null, map);
    }
    public BaseFieldView(int state, BaseField base, ATKPolygonView polygonView, ATKMap map) {
        super();
        this.state=state;
        this.base=base;
        this.polygonView = polygonView;
        this.map = map;
        if(polygonView != null) {
            this.polygonView.setData(this);
            this.polygonView.setLabelSelectedColor(BaseFieldView.LABEL_COLOR_NORMAL);
            this.polygonView.setLabelSelectedColor(BaseFieldView.LABEL_COLOR_SELECTED);
            this.setState(this.state, true);
        }
        this.update(this.base, false);
    }


    public int getState() {
        return state;
    }


    public Integer getBaseFieldId(){
        if(this.getBase() == null)  return null;
        return this.base.getId();
    }

    public BaseField getBaseField(){
        return this.getBase();
    }

    public void setBaseField(BaseField base){
        this.setBase(base);
    }

    public ATKPolygonView getPolygonView(){
        return this.polygonView;
    }

    public void setState(int newState){
        setState(newState, false);
    }
    public void setState(int newState, boolean force){
        if(this.state != newState || force){
            if(this.state == BaseFieldView.STATE_EDITING && newState != BaseFieldView.STATE_EDITING){
                //Stop drawing polygonView
                map.completePolygon();
            }

            if(newState == BaseFieldView.STATE_NORMAL){
                //Set stroke color
                this.polygonView.setStrokeColor(BaseFieldView.STROKE_COLOR_NORMAL);
                this.polygonView.setLabelSelected(false);
                this.polygonView.setFillColor(BaseFieldView.FILL_COLOR_NOT_SELECTED);
            } else if(newState == BaseFieldView.STATE_SELECTED){
                //Set stroke color and select label
                this.polygonView.setFillColor(BaseFieldView.FILL_COLOR_SELECTED);
                this.polygonView.setStrokeWidth(STROKE_WIDTH);
                this.polygonView.setStrokeColor(BaseFieldView.STROKE_COLOR_SELECTED);
                this.polygonView.setLabelSelected(true);
            } else if(newState == BaseFieldView.STATE_EDITING) {
                //Edit polygon
                map.drawPolygon(this.polygonView);
            }//else if(newState==BaseFieldView.STATE)

            this.state = newState;
        }
    }
    public void update(BaseField newField){
        this.update(newField, false);
    }
    public void update(BaseField newField, boolean force){
        //Compares data and updates map accordingly or adds to map if doesn't exist
        if(newField.getId() != -1){
//            if(newField.getDeleted()){
//                //Remove the field from the map and we are done
//                Log.d("FieldView update", "update deleted");
//                if(polygonView != null) map.removePolygon(polygonView.getAtkPolygon());
//                return;
//            }

            if(polygonView == null){
                Log.d("FieldView update", "Add polygon to map");
                ATKPolygon atkPoly = new ATKPolygon(newField.getId(), newField.getBoundary(), newField.getName());
                //This adds the polygon to the map so it should be visible after this
                polygonView = map.addPolygon(atkPoly);
                polygonView.setData(this);
                polygonView.setLabelColor(BaseFieldView.LABEL_COLOR_NORMAL);
                polygonView.setLabelSelectedColor(BaseFieldView.LABEL_COLOR_SELECTED);
                setState(this.state, true);
            }

            //Check all visual aspects of FieldView for changes

            //Field related visual aspects of polygonView
            if(polygonView.getAtkPolygon().boundary.equals(newField.getBoundary()) == false){
                //Update polygonView boundary
                Log.d("FieldView update", "Update boundary");
                polygonView.getAtkPolygon().boundary = newField.getBoundary();
            }

            if(polygonView.getAtkPolygon().label.contentEquals(newField.getName()) == false){
                //Update polygonView label
                Log.d("FieldView update", "Update name");
                polygonView.setLabel(newField.getName());
            }

            //Job related visual aspects of polygonView

            if (force==true) polygonView.setFillColor(BaseFieldView.FILL_COLOR_NOT_SELECTED);
            else polygonView.setFillColor(BaseFieldView.FILL_COLOR_NOT_BASEWORKER);






//            if(newJob == null){
//                //Update polygon fillcolor
//                Log.d("FieldView update", "Update fillcolor, no job");
// TODO SETS THE COLOR OF THE POLYGON - MAKE IT TRANSPARENT AS THIS IS THE BASE

//            } else {

//                if(newJob.getDeleted()){
//                    Log.d("FieldView update", "Update fillcolor deleted");
//                    //Update polygonView fillcolor
//                    polygonView.setFillColor(FieldView.FILL_COLOR_NOT_PLANNED);
//                }

//                if(newJob.getDeleted() == false){
//                    //Update polygonView fillcolor
//                    Log.d("FieldView update", "Update fillcolor");
//                    if(newJob.getStatus() == Job.STATUS_NOT_PLANNED){
//                        polygonView.setFillColor(FieldView.FILL_COLOR_NOT_PLANNED);
//                    } else if (newJob.getStatus() == Job.STATUS_PLANNED){
//                        polygonView.setFillColor(FieldView.FILL_COLOR_PLANNED);
//                    } else if (newJob.getStatus() == Job.STATUS_STARTED){
//                        polygonView.setFillColor(FieldView.FILL_COLOR_STARTED);
//                    } else if (newJob.getStatus() == Job.STATUS_DONE){
//                        polygonView.setFillColor(FieldView.FILL_COLOR_DONE);
//                    }
//                }
//            }

            polygonView.update();
        }
        //Done so update references
        setBase(newField);

    }

    public BaseField getBase() {
        return base;
    }

    public void setBase(BaseField base) {
        this.base = base;
    }
}
