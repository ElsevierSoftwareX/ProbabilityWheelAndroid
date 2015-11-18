package com.example.demo1;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

@SuppressLint("DrawAllocation")
public class CustomDrawableView extends View {
	//Button and drawRect: x,y is top left of the "box"
	//drawText: x,y is bottom left of the "box"
	
	//remember to change android:versionName under "AndroidManifest.xml"
	private String ver_num = "1.6";	//for displaying the version of the app
 			
 	private boolean onpg2 = false;	//on page 2
 	private boolean setVars_yet = false;	//have i set variables yet
 	private boolean header_overwrite = false;	//overwrite header when editing text
 	private int temp;
 	
 	//dimensions of smartphone's usable touch screen area
 	private int height;
 	private int length;  	
 	
 	/*all the values dont really matter b/c once i sample height and width
 	i'll call setVars and reset all these values in accordance with screen size*/
 	
 	//header
 	private String header;
 	private boolean headerpg1;	//lets me know which header string has been set, pg1 or 2
 	private int header_x;
 	private int header_y;

 	//reset button
 	private Button rst_button;
 	private String rst_str = "reset";
 	private int x_rst;
 	private int y_rst;
 	private int x_len_rst;
 	private int y_len_rst;
 	
 	//pg2 button
	private String mess_switch_button = ">";	//the message displayed for the switching pages button
	private int x_len_pg2;	//x len
	private int x_pg2;	//starting x coord
	private int y_pg2;	//starting y coord
	private int y_len_pg2;	//y len 
	private int x_pg2_sep;	//space between x_pg2 and when texts are displayed
	private int y_pg2_sep;
	
	//colorbox: square colored box that corresponds to the color of the slices
	private int x_colorbox;
	private int x_len_colorbox;
	private int y_colorbox;
	private int y_len_colorbox;
	private int y_sep_colorbox; //y value that separates each colorbox
	
	//Button(float x, float y, float Leng, float height)
	private Button pg2_button = new Button(x_pg2, y_pg2, x_len_pg2, y_len_pg2); 
	private Button label1_but;
	private Button label2_but;
	private Button label3_but;
	private Button label4_but;
	private Button label5_but;
	private Button label6_but;
	
	private Button hide_percents;
	private boolean hide = false;	//true = dont show percents
	
	//for add/remove slices
	private Button box3;
	private Button box4;
	private Button box5;
	private Button box6;

	//labels for colors & can be changed by the user through typing
	private String label1 = "Option 1";
	private String label2 = "Option 2";
	private String label3 = "Option 3";
	private String label4 = "Option 4";
	private String label5 = "Option 5";
	private String label6 = "Option 6";
	private int max_str_len = 13;
	private int whichlabel;	//tells which labels box has been touched
	
    //area of each slice
	private String area1 = ("0%");
	private String area4 = ("0%");
	private String area3 = ("0%");
	private String area2 = ("0%");
	private String area5 = ("0%");
	private String area6 = ("0%");
	
	//each slices corresponding color
	private int color1 =  Color.BLUE;
	private int color2 = Color.BLUE;
	private int color3 = Color.GREEN;
	private int color4 = Color.YELLOW;
	private int color4dark = Color.BLACK;
	private int color5 = Color.CYAN;
	private int color6 = Color.MAGENTA;	
	
	//x, y coordinate of the percentages text
	private int x_pertext;
	private int y_pertext;
	private int y_sep_pertext;
	
	//font sizes of each corresponding text
	private int hide_textsize = 0;
	private int pg1label_textsize = 0;
	private int pg2label_textsize = 0;
	private int pg1header_textsize = 0;
	private int pg2header_textsize = 0;
	private int button_textsize = 0;
	private int percent_textsize = 0;
	private int pertext_height = 0;
	private int labeltext_height = 0;
	
	//slices
	private int x_cir;	//x pos where circle starts
	private int y_cir;	//y pos 
	private int x_len_cir;	//how wide i want circle to be
	private int y_len_cir;	
	private int size_slice;	//cant remember what this is
	
	//angle each slice spans
	private int arcdeg_slice1 = 355;	//this is degrees of where red slice ends - for bounds checking
	private int ang_slice2;	
	private int ang_slice3;	
	private int ang_slice4;	
	private int ang_slice5;	
	private int ang_slice6;	
	//------------------------------------------------------------------------
	
	//knobs
	private int size_knob;	//size of the knob itself
	private int x_knob1;	//x pos of knob 1
	private int y_knob1;	
	private int x_knob2;
	private int y_knob2;
	private int x_knob3;
	private int y_knob3;
	private int x_knob4;
	private int y_knob4;
	private int x_knob5;
	private int y_knob5;
	
	private int len_knob;	//len knob from center of the circle
	private int x_ctr_knob;	//center of circle; x pos
	private int y_ctr_knob;
	
	private int touch_err_knob;	//touch sensitivity range
	private int knob_error; //min distance allowed between 2 knobs - for bounds checking
	//-------------------------------------------------------	
	
	Bitmap b = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
	Canvas c = new Canvas(b);
	
	private ShapeDrawable mDrawable;	//largest slice
    private ShapeDrawable DrawableSlice2;	
    private ShapeDrawable DrawableSlice3;	
    private ShapeDrawable DrawableSlice4;
    private ShapeDrawable DrawableSlice5;
    private ShapeDrawable DrawableSlice6;
    
    private ShapeDrawable DrawableKnob1;	//knob b/w slice 1 & 2
    private ShapeDrawable DrawableKnob2;	
    private ShapeDrawable DrawableKnob3;	
    private ShapeDrawable DrawableKnob4;	
    private ShapeDrawable DrawableKnob5;	
    
    //Knob(float x, float y, float xCtr, float yCtr, float Leng, float arcDeg, boolean touch)
    //Slice(float x, float y, float size, float startAng, float sweepAng)
    private Knob knob1 = new Knob();
    private Knob knob2 = new Knob();
    private Knob knob3 = new Knob();
    private Knob knob4 = new Knob();
    private Knob knob5 = new Knob();
    private Slice slice2 = new Slice();
    private Slice slice3 = new Slice();
    private Slice slice4 = new Slice(); 
    private Slice slice5 = new Slice(); 	
    private Slice slice6 = new Slice(); 	

	private int num_knobs = 1;
	
	Context globcon;	//for using intent and switching activities
	View view;	//global var for view
	InputMethodManager im;
	EditText ET;
	Canvas globcanvas;	
    ArrayList<Button> allButtons = new ArrayList<Button>();
    ArrayList<Button> boxButtons = new ArrayList<Button>();

    //----------------END of DECLARING VARS-------------------
    
    public CustomDrawableView(Context context) {
    	super(context);
    	
    	//sets the global variables
    	globcon=context;
    	view = this.getRootView();
    	
    	//this is for the keyboard
	    view.setFocusable(true);
	    view.setFocusableInTouchMode(true);
    	im = (InputMethodManager)globcon.getSystemService(Context.INPUT_METHOD_SERVICE);
    	
	    //ET = (EditText) findViewById(R.id.edit_message);    	
    	
    	//in case onmeasure isnt called automatically
    	//i dont think i need it though
    	//in my tests, onmeasure was called but just to be sure
    	requestLayout();
    }
    
    //prints out the keypresses from the soft keyboard upon touch
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

	char pressedKey;
	String str = "";
	
	switch (whichlabel)	//stores the appro label into str
	{
	case 1:
		str = label1;
		break;
	case 2:
		str = label2;
		break;
	case 3:
		str = label3;
		break;
	case 4:
		str = label4;
		break;
	case 5:
		str = label5;
		break;
	case 6:
		str = label6;
		break;		
	}
	
	if (keyCode == 66)	//enter button
	{	
    	im.hideSoftInputFromWindow(view.getWindowToken(), 0); //hides the keyboard and exits
		if (allButtons.get(whichlabel).getCursor())	//removes the cursor
		{
			allButtons.get(whichlabel).setCursor(false);
			str = str.substring(0, str.length() - 1);	//removes cursor
		}
		header_overwrite = false;
		header = "Investigator View";
	}
	else if (keyCode == 67)	//backspace button
	{
		if (str.length() != 1)	//dont backspace if no chars left to delete
		{
			str = str.substring(0, str.length() - 2);	//backspace removes the last char
			str += "_";
		}
		header = str;
		header_overwrite = true;
	}
	else
	{
		if (str.length()+1 != max_str_len)	//dont want to add more characters than max str len
		{
			pressedKey = (char) event.getUnicodeChar();	//converts keycode to char
			str = str.substring(0, str.length() - 1);	//removes cursor
			str = str + pressedKey;
			str += "_"; //adds cursor back
		}
		header = str;
		header_overwrite = true;
	}	
	switch (whichlabel)	//updates str back to a global var
	{
	case 1:
		label1 = str;
		break;
	case 2:
		label2 = str;
		break;
	case 3:
		label3 = str;
		break;
	case 4:
		label4 = str;
		break;
	case 5:
		label5 = str;
		break;
	case 6:
		label6 = str;
		break;		
	}
	
        // Log the Key that was pressed or do whatever you need
        // keyCode contains all the possible keyCode presses
	view.invalidate();	//goes to ondraw without needing a new motionevent (or touch)
    return super.onKeyDown(keyCode, event);
}
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
	    	float touchX = event.getX();	//this is the x and y coord in pixels that the user has touched
	    	float touchY = event.getY();
	    	
		  		    
		    if (onpg2)	//on page 2
		    {
		    	//goes thru all label buttons and pg2 button
			    for(Button a: allButtons)
			    	checkButtons(touchX,touchY, a);	
			    
			    switch (event.getAction()) 
			    {
		    	case MotionEvent.ACTION_DOWN:	//so that button will only be touched when pressed down rather than changing if you leave your finger on the button for an extended amount of time
		    		//checks whether buttons for changing slices have been touched
		    		int num_i = 3;	
			    	for (Button a: boxButtons)
			    	{
			    		checkButtons(touchX,touchY, a, num_i);
			    		num_i++;
			    	}
			    	
			    	//checks hide percents button
			    	checkButtons(touchX, touchY, hide_percents);
			    	if (hide_percents.isTouched())
			    	{
			    		hide_percents.setTouched(false);
			    		hide = !hide;
			    	}
			    	break;			    
			    }
		    }
		    
		    if (!onpg2)	//skips this if its on page 2
		    {
		    	im.hideSoftInputFromWindow(view.getWindowToken(), 0); //hides the keyboard b/c on pg1

		    	//cant call allbuttons b/c labelbuttons are part of that
		    	//should organize that soon TODO!!!
		    	checkButtons(touchX,touchY, pg2_button);	
		    	
			    /* rst_button not part of allButtons b/c then id have to change 
			     * code regarding the 6 labelbuttons b/c i used hard-coded indexes.
			     * simple fix but will do this later on. TODO!!!
			     * should prob create separate arraylist for labelbuttons to be nicer
			     */
		    	checkButtons(touchX,touchY, rst_button);	
		    	if (rst_button.isTouched())
		    	{
		    		rst_button.setTouched(false);
		    		resetCir(); //reset the pie chart
		    		setupDrawing();
			    	invalidate();	//ends func early
			    	return true;
		    	}
		    	
		    	
			    	//checks if knobs have been touched and updates the drawing of the slices and their positions
		    	switch (event.getAction()) 
		    	{
			    	case MotionEvent.ACTION_DOWN:
			    	    /*problem with this function is that when 2 knobs are close together. if i touch on the left, i want to check the left knob first
			    	     * and if touch the right side, i want to check right knob first. instead it always goes in order.
			    	     * thus if knob a and b are close together and a is checked first then b. if i touch the right side, it'll check
			    	     * knob a first (the left one) when the user probably intended to move the right knob
			    	     * find a way to check knobs in a better order or check all knobs and turn off ones that are less 
			    	     * desirable than current knob youre checking
			    	     * TODO!!!
			    	     */
			    	    checkButtons(touchX, touchY, knob1);
			    	    if(knob2.isOn() && !knob1.isTouched()){
			    	    	checkButtons(touchX, touchY, knob2);
			    	    }
			    	    if(knob3.isOn() && !knob2.isTouched()&& !knob1.isTouched()){
			    	    	checkButtons(touchX, touchY, knob3);
			    	    }    	
			    	    if(knob4.isOn() && !knob3.isTouched() && !knob2.isTouched() && !knob1.isTouched()){
			    	    	checkButtons(touchX, touchY, knob4);
			    	    }   
			    	    if(knob5.isOn() && !knob4.isTouched() && !knob3.isTouched() && !knob2.isTouched() && !knob1.isTouched()){
			    	    	checkButtons(touchX, touchY, knob5);
			    	    }      
			    	    
			    	    if(knob1.isTouched()){
			    	    	DrawableSlice2.getPaint().setColor(Color.GRAY);   	
			    	    }else if(knob2.isTouched()){
			    	    	DrawableSlice3.getPaint().setColor(Color.GRAY);    	    	
			    	    }else if(knob3.isTouched()){
			    	    	DrawableSlice4.getPaint().setColor(Color.GRAY);	    	    	
			    	    }else if(knob4.isTouched()){
			    	    	DrawableSlice5.getPaint().setColor(Color.GRAY);	    	    	
			    	    }else if(knob5.isTouched()){
			    	    	DrawableSlice6.getPaint().setColor(Color.GRAY);	    	    	
			    	    }
			    	    break;
			    	    
			    	case MotionEvent.ACTION_MOVE:
			    		if(knob1.isTouched())
			    	    {
			    	    	if(checkConflict(knob1, knob2) && knob2.isOn())
			    	    	{
			    	    		knob1.setArcDeg(knob2.getArcDeg()+knob_error);
			    	    		knob1.updateCoordinates();
			    	    		/*!!!removing settouched(false) resolved my annoying bug
			    	    		 * where when 2 knobs are close together and while touching 1 knob the whole time
			    	    		 * and you bring it close to the other knob, and when you try to move it away, 
			    	    		 * it wont move away, it gets stuck there and it was because, Motion.event.ACTION_MOVE
			    	    		 * would be called the whole time since youre finger never came off the screen
			    	    		 * thus by turning off the knob, youre stuck in a infinite loop of doing nothing until
			    	    		 * you remove your finger and get to motion.evet.ACTION_UP
			    	    		 */
			    	    		
//			    	    		knob1.setTouched(false);	
			    	    	}else if(checkConflict(knob1))
			    	    	{
			    	    		knob1.setArcDeg(arcdeg_slice1);
			    	    		knob1.updateCoordinates();
//			    	    		knob1.setTouched(false);
			    	    	}else
			    	    	{
			    	    		knob1.update(touchX,touchY,knob1);
			    	    	}	
			    	    	if ( (knob1.getArcDeg()<knob5.getArcDeg() && knob5.isOn()) ||
			    				(knob1.getArcDeg()<knob2.getArcDeg() && knob2.isOn())	)
			    	    	{
			    	    		knob1.undo();
			    	    	}
					    	changeSliceArc(slice2, knob1, 1);
					    		
					    	DrawableKnob1.setBounds((int)knob1.getxCoord(),(int)knob1.getyCoord(),(int)knob1.getxCoord()+size_knob,(int)knob1.getyCoord()+size_knob);
					    		 		
					    	DrawableSlice2 = new ShapeDrawable(new ArcShape( -slice2.getStartAng(),Math.abs(slice2.getSweepAng())));
					    	DrawableSlice2.getPaint().setColor(Color.GRAY);
					    	DrawableSlice2.setBounds(x_cir, y_cir, x_cir+x_len_cir, y_cir+y_len_cir);
			    	    	
			    	    }else if(knob2.isTouched())
			    	    {
			    	    	if(checkConflict(knob1,knob2))
			    	    	{
			    	    		knob2.setArcDeg(knob1.getArcDeg()-knob_error);
			    	    		knob2.updateCoordinates();
//			    	    		knob2.setTouched(false);	    	    	
			    	    	}else if(checkConflict(knob2,knob3) && knob3.isOn())
			    	    	{
			    	    		knob2.setArcDeg(knob3.getArcDeg()+knob_error);
			    	    		knob2.updateCoordinates();
//			    	    		knob2.setTouched(false);
			    	    	}else if(checkConflict(knob2))
			    	    	{
			    	    		knob2.setArcDeg(knob_error);
			    	    		knob2.updateCoordinates();
//			    	    		knob2.setTouched(false);
			    	    	}else
			    	    	{
				    	    	knob2.update(touchX,touchY,knob2);
			    	    	}
			    	    	//checks if knobs go over the bounds of other knobs
			    	    	if ( knob2.getArcDeg()>knob1.getArcDeg() ||
			    				(knob2.getArcDeg()<knob3.getArcDeg() && knob3.isOn())	)
			    	    	{
			    	    		knob2.undo();
			    	    	}
				   	    	changeSliceArc(slice3, knob2, 2);
					    		
				    		DrawableKnob2.setBounds((int)knob2.getxCoord(),(int)knob2.getyCoord(),(int)knob2.getxCoord()+size_knob,(int)knob2.getyCoord()+size_knob);
					    		 		
				    		DrawableSlice3 = new ShapeDrawable(new ArcShape( -slice3.getStartAng(),slice3.getSweepAng()));
				    		DrawableSlice3.getPaint().setColor(Color.GRAY);
				    		DrawableSlice3.setBounds(x_cir, y_cir, x_cir+x_len_cir, y_cir+y_len_cir);	    	    
			    	    }else if(knob3.isTouched())
			    	    {
			    	    	if(checkConflict(knob2,knob3))
			    	    	{
			    	    		knob3.setArcDeg(knob2.getArcDeg()-knob_error);
			    	    		knob3.updateCoordinates();
//			    	    		knob3.setTouched(false);
			    	    	
			    	    	}else if(checkConflict(knob3,knob4) && knob4.isOn())
			    	    	{
			    	    		knob3.setArcDeg(knob4.getArcDeg()+knob_error);
			    	    		knob3.updateCoordinates();
//			    	    		knob3.setTouched(false);
			    	    	}else if(checkConflict(knob3))
			    	    	{
			    	    		knob3.setArcDeg(knob_error);
			    	    		knob3.updateCoordinates();
//			    	    		knob3.setTouched(false);
			    	    	}else
			    	    	{
			    	    		knob3.update(touchX,touchY,knob3);
			    	    	}
			    	    	//checks if knobs go over the bounds of other knobs
			    	    	if ( knob3.getArcDeg()>knob2.getArcDeg() ||
			    				(knob3.getArcDeg()<knob4.getArcDeg() && knob4.isOn())	)
			    	    	{
			    	    		knob3.undo();
			    	    	}
				   	    	changeSliceArc(slice4, knob3, 3);
					    		
				    		DrawableKnob3.setBounds((int)knob3.getxCoord(),(int)knob3.getyCoord(),(int)knob3.getxCoord()+size_knob,(int)knob3.getyCoord()+size_knob);
					    		 		
				    		DrawableSlice4 = new ShapeDrawable(new ArcShape( -slice4.getStartAng(),slice4.getSweepAng()));
				    		DrawableSlice4.getPaint().setColor(Color.GRAY);
				    		DrawableSlice4.setBounds(x_cir, y_cir, x_cir+x_len_cir, y_cir+y_len_cir);	    	    
			    	    }else if(knob4.isTouched())
			    	    {
			    	    	if(checkConflict(knob3,knob4))
			    	    	{
			    	    		knob4.setArcDeg(knob3.getArcDeg()-knob_error);
			    	    		knob4.updateCoordinates();
//			    	    		knob4.setTouched(false);
			    	    	
			    	    	}else if(checkConflict(knob4,knob5) && knob5.isOn())
			    	    	{
			    	    		knob4.setArcDeg(knob5.getArcDeg()+knob_error);
			    	    		knob4.updateCoordinates();
//			    	    		knob4.setTouched(false);
			    	    	}else if(checkConflict(knob4))
			    	    	{
			    	    		knob4.setArcDeg(knob_error);
			    	    		knob4.updateCoordinates();
//			    	    		knob4.setTouched(false);
			    	    	}else
			    	    	{
			    	    		knob4.update(touchX,touchY,knob4);
			    	    	}
			    	    	//checks if knobs go over the bounds of other knobs
			    	    	if ( knob4.getArcDeg()>knob3.getArcDeg() ||
			    				(knob4.getArcDeg()<knob5.getArcDeg() && knob5.isOn())	)
			    	    	{
			    	    		knob4.undo();
			    	    	}
				   	    	changeSliceArc(slice5, knob4, 4);
					    		
				    		DrawableKnob4.setBounds((int)knob4.getxCoord(),(int)knob4.getyCoord(),(int)knob4.getxCoord()+size_knob,(int)knob4.getyCoord()+size_knob);
					    		 		
				    		DrawableSlice5 = new ShapeDrawable(new ArcShape( -slice5.getStartAng(),slice5.getSweepAng()));
				    		DrawableSlice5.getPaint().setColor(Color.GRAY);
				    		DrawableSlice5.setBounds(x_cir, y_cir, x_cir+x_len_cir, y_cir+y_len_cir);
			    	    
			    	    }else if(knob5.isTouched())
			    	    {
			    	    	if(checkConflict(knob4,knob5))
			    	    	{
			    	    		knob5.setArcDeg(knob4.getArcDeg()-knob_error);
			    	    		knob5.updateCoordinates();
//			    	    		knob5.setTouched(false);
			    	    	
			    	    	}else if(checkConflict(knob5))
			    	    	{
			    	    		knob5.setArcDeg(knob_error);
			    	    		knob5.updateCoordinates();
//			    	    		knob5.setTouched(false);
			    	    	}else
			    	    	{
			    	    		knob5.update(touchX,touchY,knob5);
			    	    	}
			    	    	if ( knob5.getArcDeg()>knob4.getArcDeg() ||
			    	    			knob5.getArcDeg()>knob1.getArcDeg()	)
			    	    	{
			    	    		knob5.undo();
			    	    	}
			
				   	    	changeSliceArc(slice6, knob5, 5);
				    		
				    		DrawableKnob5.setBounds((int)knob5.getxCoord(),(int)knob5.getyCoord(),(int)knob5.getxCoord()+size_knob,(int)knob5.getyCoord()+size_knob);
					    		 		
				    		DrawableSlice6 = new ShapeDrawable(new ArcShape( -slice6.getStartAng(),slice6.getSweepAng()));
				    		DrawableSlice6.getPaint().setColor(Color.GRAY);
				    		DrawableSlice6.setBounds(x_cir, y_cir, x_cir+x_len_cir, y_cir+y_len_cir);
			    	    }	
			    	    
			    	    break;
			    	case MotionEvent.ACTION_UP:
			    	    knob1.setTouched(false);
			    	    knob2.setTouched(false);
			    	    knob3.setTouched(false);
			    	    knob4.setTouched(false);
			    	    knob5.setTouched(false);
			    	    
			    	    
			    	    switch (num_knobs)
			     	    {
			    		case 5:
				    	    DrawableSlice6.getPaint().setColor(color6);
			     		case 4:
				    	    DrawableSlice5.getPaint().setColor(color5);
			     		case 3:
				    	    DrawableSlice4.getPaint().setColor(color4);
			     		case 2:
				    	    DrawableSlice3.getPaint().setColor(color3);
			     		case 1:
				    	    DrawableSlice2.getPaint().setColor(color2);
			     		}	    	    
			    	 
			    	    break;
			    	default:
			    	    return false;
		    	}
		    }    	
    	invalidate();
  		return true;	    
    }
    
    private boolean checkConflict(Knob a, Knob b) { //checks if the knobs are in conflict b/c dont want a slice to overtake another slice
		if(Math.abs(b.getArcDeg()-a.getArcDeg())>1)
			return false;
		return true;	
	}
         
    private boolean checkConflict(Knob a) {	//checks if knob a is in conflict. note: theres only 1 knob next to a not the usual 2
		if(Math.abs(a.getArcDeg())>1&&Math.abs(a.getArcDeg())<(arcdeg_slice1+1))
			return false;
		return true;
	}

    //checking the knobs
	public void checkButtons(float touchX, float touchY, Knob a){
    	//a.getxCoord() is left side of knob
		//y is top of knob
		if(touchX<=(a.getxCoord()+size_knob+touch_err_knob)&&
    			touchX>=(a.getxCoord()-touch_err_knob)&&
    			touchY<=(a.getyCoord()+size_knob+touch_err_knob)&&
    			touchY>=(a.getyCoord()-touch_err_knob))
    	{
    		a.setTouched(true);
    	}    	
    }
	
	public void pg_2clearButtons()	//clears the touch of the switching page button
	{
		pg2_button.setTouched(false);
	}
    
	//checking the buttons if theyve been touched
	public void checkButtons(float touchX, float touchY, Button a){
    	if(touchX<=(a.getxCoord()+a.getLength())&&
    			touchX>=a.getxCoord()&&
    			touchY<=(a.getyCoord())+a.getHeight()&&
    			touchY>=a.getyCoord())
    	{
    		a.setTouched(true);
    	}
    }
	
	//checks buttons for changing num of slices
	public void checkButtons(float touchX, float touchY, Button a, int i){
    	if(touchX<=(a.getxCoord()+a.getLength())&&
    			touchX>=a.getxCoord()&&
    			touchY<=(a.getyCoord())+a.getHeight()&&
    			touchY>=a.getyCoord())
    	{   
	    		if (a.isTouched())	//need to uncheck the button
	    		{
	    			if ( (num_knobs+1) == i)
	    				{
	    					a.setTouched(false);
	    					num_knobs--;
	//    		    		reset_pg1();	//so it goes to pg1 immediately after changing # slices
	    		    		resetCir(); //reset the pie chart
	    		    		setupDrawing();
	    				}
	    		}
	    		else	//need to check the button
	    			if ((num_knobs+2) == i)
					{
						a.setTouched(true);
						num_knobs++;
	//		    		reset_pg1();	//so it goes to pg1 immediately after changing # slices
			    		resetCir(); //reset the pie chart
			    		setupDrawing();
	
					}	
    	}
    }
	
	//gets the dimensions of teh phone in use. used to scale the app appropriately
	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec)
	{
		//measures height and width of screen excluding bars
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    length = MeasureSpec.getSize(widthMeasureSpec);
	    height = MeasureSpec.getSize(heightMeasureSpec);

	    //so that it will only run once
	    if (!setVars_yet)
	    {
	    	setVars();
	    	setVars_yet=true;
	    }
	}
	
    public void setupDrawing(){
    	//draws each slice at the beginning of the app
    	
    	mDrawable = new ShapeDrawable(new OvalShape());
	    mDrawable.getPaint().setColor(color1);
	    mDrawable.setBounds(x_cir, y_cir , x_cir+x_len_cir, y_cir+y_len_cir);
	    
	    DrawableSlice2 = new ShapeDrawable(new ArcShape(slice2.getStartAng(),slice2.getSweepAng()));
	    DrawableSlice2.getPaint().setColor(color2);
	    DrawableSlice2.setBounds((int)slice2.getxCoord(), (int)slice2.getyCoord(), x_cir+x_len_cir, y_cir+y_len_cir);
	    
	    DrawableSlice3 = new ShapeDrawable(new ArcShape( slice3.getStartAng(),slice3.getSweepAng()));
	    DrawableSlice3.getPaint().setColor(color3);
	    DrawableSlice3.setBounds((int)slice3.getxCoord(),(int)slice3.getyCoord(), x_cir+x_len_cir, y_cir+y_len_cir);   
    
	    DrawableSlice4 = new ShapeDrawable(new ArcShape( slice4.getStartAng(),slice4.getSweepAng()));
	    DrawableSlice4.getPaint().setColor(color4);
	    DrawableSlice4.setBounds((int)slice4.getxCoord(),(int)slice4.getyCoord(), x_cir+x_len_cir, y_cir+y_len_cir);   
	    
	    DrawableSlice5 = new ShapeDrawable(new ArcShape( slice5.getStartAng(),slice5.getSweepAng()));
	    DrawableSlice5.getPaint().setColor(color5);
	    DrawableSlice5.setBounds((int)slice5.getxCoord(),(int)slice5.getyCoord(), x_cir+x_len_cir, y_cir+y_len_cir);
	    
	    DrawableSlice6 = new ShapeDrawable(new ArcShape( slice6.getStartAng(),slice6.getSweepAng()));
	    DrawableSlice6.getPaint().setColor(color6);
	    DrawableSlice6.setBounds((int)slice6.getxCoord(),(int)slice6.getyCoord(), x_cir+x_len_cir, y_cir+y_len_cir);
	    
	    DrawableKnob1 = new ShapeDrawable(new OvalShape());
	    DrawableKnob1.getPaint().setColor(Color.BLACK);
	    DrawableKnob1.setBounds(x_knob1, y_knob1, x_knob1+size_knob, y_knob1+size_knob);
    	
	    DrawableKnob2 = new ShapeDrawable(new OvalShape());
	    DrawableKnob2.getPaint().setColor(Color.BLACK);
	    DrawableKnob2.setBounds(x_knob2, y_knob2, x_knob2+size_knob, y_knob2+size_knob);
	    
	    DrawableKnob3 = new ShapeDrawable(new OvalShape());
	    DrawableKnob3.getPaint().setColor(Color.BLACK);
	    DrawableKnob3.setBounds(x_knob3, y_knob3, x_knob3+size_knob, y_knob3+size_knob);
	    
	    DrawableKnob4 = new ShapeDrawable(new OvalShape());
	    DrawableKnob4.getPaint().setColor(Color.BLACK);
	    DrawableKnob4.setBounds(x_knob4, y_knob4, x_knob4+size_knob, y_knob4+size_knob);
	    
	    DrawableKnob5 = new ShapeDrawable(new OvalShape());
	    DrawableKnob5.getPaint().setColor(Color.BLACK);
	    DrawableKnob5.setBounds(x_knob5, y_knob5, x_knob5+size_knob, y_knob5+size_knob);
    }
        
 	public void setVars ()
 	{	//i have to set the variables again because now the apps dimensions have been sampled.
 		//this method is only called once
 		
 	 	//pg2 button
 		 x_len_pg2 = 175*length/1080;	//x len
		 y_len_pg2 = x_len_pg2;	//y len
 		 x_pg2 = length - x_len_pg2;	//starting x coord
 		 y_pg2 = 0;	//starting y coord
 		 x_pg2_sep = 20*x_len_pg2/175;	//space between x_pg2 and when texts are displayed
 		 y_pg2_sep = x_pg2_sep;
 		
 		 //header
 	 	 header_x = x_len_pg2 + x_len_pg2/3;
 	 	 header_y = x_len_pg2*5/6;
 		 
 		//Button(float x, float y, float Leng, float height)
 		pg2_button.setButton(x_pg2, y_pg2, x_len_pg2, y_len_pg2); 
 		hide_percents = new Button(x_pg2, y_pg2, x_len_pg2, y_len_pg2);
 		
 		size_knob = 60*length/1080;	//size of the knob itself 
 		
 		//---------------------------------------------
 		//resets colorbox position
    	x_len_colorbox = 50*length/1080;
      	y_len_colorbox = x_len_colorbox;
    	y_sep_colorbox = y_len_colorbox + y_len_colorbox/4;	//y value that separates each colorbox
    	x_colorbox = size_knob;
    	temp = 10*length/1080;
    	y_colorbox = height - y_sep_colorbox*6 - temp;
 				
    	//sets the reset button
    	x_len_rst = 3*x_len_colorbox;
    	y_len_rst = 2*y_len_colorbox;
    	x_rst = length - y_sep_colorbox - x_len_rst;
    	y_rst = height - y_sep_colorbox - y_len_rst;
    	rst_button = new Button(x_rst, y_rst, x_len_rst, y_len_rst);

 		x_cir = size_knob;	//x pos where slice starts
 		y_cir = header_y+size_knob+size_knob/2;
 		y_len_cir = y_colorbox - size_knob - y_cir;//how wide i want circle to be
 		
 		//checks for condition that the piechart goes out of bounds on x plane
 		temp = length - size_knob*2;
 		if ( temp < y_len_cir)
 			y_len_cir = temp;
 		
 		x_len_cir = y_len_cir;	//how wide i want circle to be
 		size_slice = y_len_cir/4;	//cant remember what this is
 		
 		//slice angles (how much it spans)
 		ang_slice2 = 0;	
 		ang_slice3 = 0;	
 		ang_slice4 = 0;	
 		ang_slice5 = 0;	
 		ang_slice6 = 0;	
 		switch (num_knobs)
 		{
 		case 1:
 	 		ang_slice2 = 360/(num_knobs+1) * 1;	
 	 		break;
 		case 2:
 	 		ang_slice2 = 360/(num_knobs+1) * 2;	
 	 		ang_slice3 = 360/(num_knobs+1) * 1;
 	 		break;
 		case 3:
 	 		ang_slice2 = 360/(num_knobs+1) * 3;	
 	 		ang_slice3 = 360/(num_knobs+1) * 2;
 	 		ang_slice4 = 360/(num_knobs+1) * 1;
 	 		break;
 		case 4:
 	 		ang_slice2 = 360/(num_knobs+1) * 4;	
 	 		ang_slice3 = 360/(num_knobs+1) * 3;
 	 		ang_slice4 = 360/(num_knobs+1) * 2;
 	 		ang_slice5 = 360/(num_knobs+1) * 1;
 	 		break;
 		case 5:
 	 		ang_slice2 = 360/(num_knobs+1) * 5;	
 	 		ang_slice3 = 360/(num_knobs+1) * 4;
 	 		ang_slice4 = 360/(num_knobs+1) * 3;
 	 		ang_slice5 = 360/(num_knobs+1) * 2;
 	 		ang_slice6 = 360/(num_knobs+1) * 1;
 	 		break;
 		}
 		
 		double dpos;
 		int ipos;
 		switch (num_knobs)
 		{
 		case 5:
 	 		dpos = Math.cos(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob5 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob5 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob4 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob4 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice4));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob3 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice4));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob3 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice5));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob2 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice5));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob2 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice6));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob1 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice6));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob1 = y_cir + ipos - size_knob/2;
 	 		break;
 		case 4:
 	 		dpos = Math.cos(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob4 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob4 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob3 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob3 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice4));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob2 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice4));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob2 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice5));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob1 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice5));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob1 = y_cir + ipos - size_knob/2;
 	 		break;
 		case 3:
 	 		dpos = Math.cos(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob3 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob3 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob2 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob2 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice4));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob1 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice4));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob1 = y_cir + ipos - size_knob/2;
 	 		break;
 		case 2:
 	 		dpos = Math.cos(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob2 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob2 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob1 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob1 = y_cir + ipos - size_knob/2;
 	 		break;
 		case 1:
 	 		dpos = Math.cos(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob1 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob1 = y_cir + ipos - size_knob/2;
 	 		break;
 		}
 		
 		len_knob = x_len_cir/2;	//len knob from center
 		x_ctr_knob = x_len_cir/2 + x_cir - size_knob/4;	//center of circle; x pos
 		y_ctr_knob = y_len_cir/2 + y_cir - size_knob/4;
 		
 		touch_err_knob = size_knob/3;	//touch sensitivity range
 		knob_error = size_knob/24; //min distance allowed between 2 knobs - for bounds checking
 		if (knob_error < 2)	//min knob error
 			knob_error = 2;
 		//-------------------------------------------------------	
 	    knob1.setKnob(x_knob1, y_knob1, x_ctr_knob, y_ctr_knob, len_knob, ang_slice2,false, true);
 	    knob2.setKnob(x_knob2, y_knob2, x_ctr_knob, y_ctr_knob, len_knob, ang_slice3,false, false);
 	    knob3.setKnob(x_knob3, y_knob3, x_ctr_knob, y_ctr_knob, len_knob, ang_slice4,false, false);
 	    knob4.setKnob(x_knob4, y_knob4, x_ctr_knob, y_ctr_knob, len_knob, ang_slice5,false, false);
 	    knob5.setKnob(x_knob5, y_knob5, x_ctr_knob, y_ctr_knob, len_knob, ang_slice6,false, false);
 	    
 	    switch (num_knobs)	//sets whether the knob is active or not
 	    {
		case 5:
			knob5.setOn(true);
 		case 4:
			knob4.setOn(true);
 		case 3:
			knob3.setOn(true);
 		case 2:
			knob2.setOn(true);
 		}
 	    
 	    slice2.setSlice(x_cir, y_cir, size_slice, -1*ang_slice2, ang_slice2);
 	    slice3.setSlice(x_cir, y_cir, size_slice, -1*ang_slice3, ang_slice3);	
 	    slice4.setSlice(x_cir, y_cir, size_slice, -1*ang_slice4, ang_slice4); 
 	    slice5.setSlice(x_cir, y_cir, size_slice, -1*ang_slice5, ang_slice5); 
 	    slice6.setSlice(x_cir, y_cir, size_slice, -1*ang_slice6, ang_slice6); 
 	    
 		Color customcolor = new Color();
 		int orange = customcolor.rgb(255, 153, 0);
 		color2 = orange;
 		
 	   allButtons.add(pg2_button);
 	   
	   setupDrawing();	
//	   resetCir();
	   
 	}
 	
 	public void resetCir ()
 	{
 		//slice angles (how much it spans)
 		ang_slice2 = 0;	
 		ang_slice3 = 0;	
 		ang_slice4 = 0;	
 		ang_slice5 = 0;	
 		ang_slice6 = 0;	
 		switch (num_knobs)
 		{
 		case 1:
 	 		ang_slice2 = 360/(num_knobs+1) * 1;	
 	 		break;
 		case 2:
 	 		ang_slice2 = 360/(num_knobs+1) * 2;	
 	 		ang_slice3 = 360/(num_knobs+1) * 1;
 	 		break;
 		case 3:
 	 		ang_slice2 = 360/(num_knobs+1) * 3;	
 	 		ang_slice3 = 360/(num_knobs+1) * 2;
 	 		ang_slice4 = 360/(num_knobs+1) * 1;
 	 		break;
 		case 4:
 	 		ang_slice2 = 360/(num_knobs+1) * 4;	
 	 		ang_slice3 = 360/(num_knobs+1) * 3;
 	 		ang_slice4 = 360/(num_knobs+1) * 2;
 	 		ang_slice5 = 360/(num_knobs+1) * 1;
 	 		break;
 		case 5:
 	 		ang_slice2 = 360/(num_knobs+1) * 5;	
 	 		ang_slice3 = 360/(num_knobs+1) * 4;
 	 		ang_slice4 = 360/(num_knobs+1) * 3;
 	 		ang_slice5 = 360/(num_knobs+1) * 2;
 	 		ang_slice6 = 360/(num_knobs+1) * 1;
 	 		break;
 		}
 		
 		//sets x,y coord of knobs depending on how many slices active
		double dpos;
 		int ipos;
 		switch (num_knobs)
 		{
 		case 5:
 	 		dpos = Math.cos(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob5 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob5 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob4 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob4 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice4));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob3 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice4));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob3 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice5));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob2 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice5));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob2 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice6));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob1 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice6));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob1 = y_cir + ipos - size_knob/2;
 	 		break;
 		case 4:
 	 		dpos = Math.cos(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob4 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob4 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob3 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob3 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice4));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob2 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice4));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob2 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice5));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob1 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice5));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob1 = y_cir + ipos - size_knob/2;
 	 		break;
 		case 3:
 	 		dpos = Math.cos(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob3 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob3 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob2 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob2 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice4));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob1 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice4));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob1 = y_cir + ipos - size_knob/2;
 	 		break;
 		case 2:
 	 		dpos = Math.cos(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob2 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob2 = y_cir + ipos - size_knob/2;
 	 		
 	 		dpos = Math.cos(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob1 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice3));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob1 = y_cir + ipos - size_knob/2;
 	 		break;
 		case 1:
 	 		dpos = Math.cos(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * x_len_cir;
 	 		ipos = (int) dpos;
 	 		x_knob1 = x_cir + ipos - size_knob/2;	
 	 		dpos = Math.sin(Math.toRadians(ang_slice2));
 	 		dpos = (dpos+1)/2 * y_len_cir;
 	 		ipos = (int) dpos;
 	 		y_knob1 = y_cir + ipos - size_knob/2;
 	 		break;
 		}
 		
 		knob1.setKnob(x_knob1, y_knob1, x_ctr_knob, y_ctr_knob, len_knob, ang_slice2,false, true);
  	    knob2.setKnob(x_knob2, y_knob2, x_ctr_knob, y_ctr_knob, len_knob, ang_slice3,false, false);
  	    knob3.setKnob(x_knob3, y_knob3, x_ctr_knob, y_ctr_knob, len_knob, ang_slice4,false, false);
  	    knob4.setKnob(x_knob4, y_knob4, x_ctr_knob, y_ctr_knob, len_knob, ang_slice5,false, false);
  	    knob5.setKnob(x_knob5, y_knob5, x_ctr_knob, y_ctr_knob, len_knob, ang_slice6,false, false);
  	    
  	    switch (num_knobs)	//sets whether the knob is active or not
  	    {
 		case 5:
 			knob5.setOn(true);
  		case 4:
 			knob4.setOn(true);
  		case 3:
 			knob3.setOn(true);
  		case 2:
 			knob2.setOn(true);
  		}
  	    
  	    slice2.setSlice(x_cir, y_cir, size_slice, -1*ang_slice2, ang_slice2);
  	    slice3.setSlice(x_cir, y_cir, size_slice, -1*ang_slice3, ang_slice3);	
  	    slice4.setSlice(x_cir, y_cir, size_slice, -1*ang_slice4, ang_slice4); 
  	    slice5.setSlice(x_cir, y_cir, size_slice, -1*ang_slice5, ang_slice5); 
  	    slice6.setSlice(x_cir, y_cir, size_slice, -1*ang_slice6, ang_slice6); 
  	
 	}
 	
 	//adds an underscore to act as a "cursor" for typing
 	public void cursor()
 	{ 	
 		String str = "";
 		switch (whichlabel)
 		{
	 	case 1:
			str = label1;
			break;
		case 2:
			str = label2;
			break;
		case 3:
			str = label3;
			break;
		case 4:
			str = label4;
			break;
		case 5:
			str = label5;
			break;
		case 6:
			str = label6;
			break;	
 		}
 		
 		str = str + "_";
		
 		switch (whichlabel)
 		{
 		case 1:
 			label1 = str;
 			break;
 		case 2:
 			label2 = str;
 			break;
 		case 3:
 			label3 = str;
 			break;
 		case 4:
 			label4 = str;
 			break;
 		case 5:
 			label5 = str;
 			break;
 		case 6:
 			label6 = str;
 			break;		
 		}
 	}
 	
 	//removes cursor in appro label
 	public void removecursor(int i)
 	{
 		String str = "";
 		switch (i)
 		{
	 	case 1:
			str = label1;
			break;
		case 2:
			str = label2;
			break;
		case 3:
			str = label3;
			break;
		case 4:
			str = label4;
			break;
		case 5:
			str = label5;
			break;
		case 6:
			str = label6;
			break;	
 		}
 		
 		str = str.substring(0, str.length() - 1);
		
 		switch (i)
 		{
 		case 1:
 			label1 = str;
 			break;
 		case 2:
 			label2 = str;
 			break;
 		case 3:
 			label3 = str;
 			break;
 		case 4:
 			label4 = str;
 			break;
 		case 5:
 			label5 = str;
 			break;
 		case 6:
 			label6 = str;
 			break;		
 		}
 	}
 	
	//finds which button is touched for user-input labels
    public void keyboard_check ()
    {
    	boolean touched = false;

    	if (allButtons.get(1).isTouched())
    	{
        	//button touched 1st time, so remove entire text
        	if (!allButtons.get(1).isTouched_yet())
        		label1 = "";
        	allButtons.get(1).setTouched_yet(true);
    		whichlabel = 1;
    		touched = true;
    		header = label1;
    		allButtons.get(whichlabel).setTouched_yet(true);
    	}
    	else if (allButtons.get(2).isTouched())
    	{
        	//button touched 1st time, so remove entire text
        	if (!allButtons.get(2).isTouched_yet())
        		label2 = "";
        	allButtons.get(2).setTouched_yet(true);
    		whichlabel = 2;
    		touched = true;
    		header = label2;
    	}
    	else if (allButtons.get(3).isTouched())
		{
        	//button touched 1st time, so remove entire text
        	if (!allButtons.get(3).isTouched_yet())
        		label3 = "";
        	allButtons.get(3).setTouched_yet(true);
    		whichlabel = 3;
    		touched = true;
    		header = label3;
		}
    	else if (allButtons.get(4).isTouched())
		{
        	//button touched 1st time, so remove entire text
        	if (!allButtons.get(4).isTouched_yet())
        		label4 = "";
        	allButtons.get(4).setTouched_yet(true);
    		whichlabel = 4;
    		touched = true;
    		header = label4;
		}
    	else if (allButtons.get(5).isTouched())
		{
        	//button touched 1st time, so remove entire text
        	if (!allButtons.get(5).isTouched_yet())
        		label5 = "";
        	allButtons.get(5).setTouched_yet(true);
    		whichlabel = 5;
    		touched = true;
    		header = label5;
		}
    	else if (allButtons.get(6).isTouched())
		{
        	//button touched 1st time, so remove entire text
        	if (!allButtons.get(6).isTouched_yet())
        		label6 = "";
        	allButtons.get(6).setTouched_yet(true);
    		whichlabel = 6;
    		touched = true;
    		header = label6;
		}
    	if (touched)
    	{
        	for (int i = 1; i < 7; i++)	//removes all cursors that are true
        	{
        		if (allButtons.get(i).getCursor())
        		{
        			removecursor(i);
        			allButtons.get(i).setCursor(false);
        		}
        	}
        	header_overwrite  = true;
    		allButtons.get(whichlabel).setTouched(false);    			
    		allButtons.get(whichlabel).setCursor(true);	//adds cursor to the label being touched
    		cursor();
		    im.showSoftInput(view, InputMethodManager.SHOW_FORCED);	//brings up the keyboard
    	}
    }
    
    public void onDraw(Canvas canvas) {

	    Paint g = new Paint();
	    
	    temp = 40*length/1080;
	    //prints version number
	    g.setTextSize(temp);
	    g.setColor(Color.BLACK);
	    String version_word = "Version: " + ver_num;
	    int wordlen = (int) g.measureText(version_word);
	    int temp3 = 40*length/1080;
	    temp = temp3 / 4;
	    canvas.drawText(version_word, length-wordlen-temp, height-temp, g);	   
//	    canvas.drawText(version_word, 20, 50, g);	//for debugging purpsoes
	    
	    if (onpg2)	//checks if buttons for keyboard input has been pressed
	    	keyboard_check();
	    
	    if(allButtons.get(0).isTouched() && !onpg2)
	    {	//going to pg2
	    	//changes vars for pg2
	    	reset_pg2();
		    
	    	page2(canvas);
	    	pg_2clearButtons();
	    }
	    else if (onpg2 && !allButtons.get(0).isTouched() )
	    {	//already on pg2 so continue on pg2
	    	page2(canvas);
	    }
	    else	//need to go back to 1st page or continue on pg1
	    {
	    	
	    	if ( allButtons.get(0).isTouched() )
	    	{
	    		pg_2clearButtons();	
	    		
	    		for (int i = 1; i < 7; i++)	//removes all cursors that are true
	        	{
	        		if (allButtons.get(i).getCursor())
	        		{
	        			removecursor(i);
	        			allButtons.get(i).setCursor(false);
	        		}
	        	}
	    		header_overwrite = false;
	    	}
	    	
	    	reset_pg1();
	    	
			//draws slices
			mDrawable.draw(canvas);
			DrawableSlice2.draw(canvas);
			DrawableKnob1.draw(canvas);
		    switch (num_knobs)	
	 	    {
		    case 2:
				DrawableSlice3.draw(canvas);
				DrawableKnob2.draw(canvas);
				break;
		    case 3:
				DrawableSlice3.draw(canvas);
				DrawableKnob2.draw(canvas);
				DrawableSlice4.draw(canvas);
				DrawableKnob3.draw(canvas);
				break;
		    case 4:
				DrawableSlice3.draw(canvas);
				DrawableKnob2.draw(canvas);
				DrawableSlice4.draw(canvas);
				DrawableKnob3.draw(canvas);
				DrawableSlice5.draw(canvas);
				DrawableKnob4.draw(canvas);
				break;
		    case 5:
				DrawableSlice3.draw(canvas);
				DrawableKnob2.draw(canvas);
				DrawableSlice4.draw(canvas);
				DrawableKnob3.draw(canvas);
				DrawableSlice5.draw(canvas);
				DrawableKnob4.draw(canvas);
				DrawableSlice6.draw(canvas);
				DrawableKnob5.draw(canvas);
				break;
	 		}
		    
			
			int buff = x_len_colorbox/5;
			if (buff < 10*length/1080)
				buff = 10*length/1080;
			int temp_x = x_colorbox + x_len_colorbox + buff;
			int temp_y = y_colorbox + y_len_colorbox - buff;
		
			
			if (pg1label_textsize == 0)	//finds the label text size if it hasnt beendone yet. it only runs once
			{
				//finding approriate text size for label names
				Rect bounds = new Rect();
				pg1label_textsize = 40*length/1080;
				pg1label_textsize = pg1label_textsize/2;
				g.setTextSize(pg1label_textsize);
			    g.getTextBounds(label1, 0, label1.length(), bounds);
			    int text_height = bounds.height();	//current height
				temp = x_len_colorbox - 2*buff;	//max height i want
				while ( text_height < temp)
				{
					pg1label_textsize++;
					g.setTextSize(pg1label_textsize);
				    g.getTextBounds(label1, 0, label1.length(), bounds);
				    text_height = bounds.height();
				}
				pg1label_textsize--;
			}
		
			int temp_color3 = color3;
			int temp_color4 = color4;
			int temp_color4dark = color4dark;
			int temp_color5 = color5;
			int temp_color6 = color6;
			if (headerpg1)	//on pg1
			{
				switch(num_knobs)
				{				
				case 1:	//only 5 slices
					color3 = Color.WHITE;
				case 2:	//only 4 slices
					color4 = Color.WHITE;
					color4dark = Color.WHITE;
				case 3:	//only 3 slices
					color5 = Color.WHITE;
				case 4:	//only 2 slices
					color6 = Color.WHITE;	//"turns off" label6

				}
			}
			
		    //draws each label names in their corresponding color
			g.setTextSize (pg1label_textsize);
			g.setColor (color1);
			canvas.drawText(label1, temp_x , temp_y , g);
			g.setColor (color2);
			canvas.drawText(label2, temp_x , temp_y+y_sep_colorbox , g);
			g.setColor (color3);
			canvas.drawText(label3, temp_x , temp_y+y_sep_colorbox*2 , g);
			g.setColor (color4dark);
			canvas.drawText(label4, temp_x , temp_y+y_sep_colorbox*3 , g);
			g.setColor (color5);
			canvas.drawText(label5, temp_x , temp_y+y_sep_colorbox*4 , g);
			g.setColor (color6);
			canvas.drawText(label6, temp_x , temp_y+y_sep_colorbox*5 , g);
					
			if (!hide)
			{
			    calculateArea();
			    //text of percentages on pg 1
			    //---to find new x position
			    g.setTextSize(pg1label_textsize);
				String label = "Short Label 1";
			    int textlen = (int) g.measureText(label);
			    temp_x = temp_x + textlen + buff;
			    //---
				g.setColor (color1);
			    canvas.drawText(area1, temp_x, temp_y,g);
				g.setColor (color2);
			    canvas.drawText(area2, temp_x, temp_y+y_sep_colorbox,g);
				g.setColor (color3);
			    canvas.drawText(area3, temp_x, temp_y+2*y_sep_colorbox,g);
				g.setColor (color4dark);
			    canvas.drawText(area4, temp_x, temp_y+3*y_sep_colorbox,g);	
				g.setColor (color5);
			    canvas.drawText(area5, temp_x, temp_y+4*y_sep_colorbox,g);
				g.setColor (color6);
			    canvas.drawText(area6, temp_x, temp_y+5*y_sep_colorbox,g);
			}    
						
		    //sets the colors back to normal
			color3 = temp_color3;
			color4 = temp_color4;
			color4dark = temp_color4dark;
			color5 = temp_color5;
			color6 = temp_color6;
			
			//draws the reset button
			g.setTextSize (pg1label_textsize);
			g.setColor(Color.RED);
		    canvas.drawRect(new Rect(x_rst, y_rst, x_rst+x_len_rst, y_rst+y_len_rst),g);
			g.setColor(Color.WHITE);
			//get height and lenght of string
			Rect bounds = new Rect();
		    g.getTextBounds(rst_str, 0, rst_str.length(), bounds);
		    int text_height = bounds.height();
		    wordlen = (int) g.measureText(rst_str);
		    temp_x = x_rst + (x_len_rst - wordlen)/2;
		    temp_y = y_rst + y_len_rst - (y_len_rst - text_height)/2;
			canvas.drawText(rst_str, temp_x , temp_y , g);
			
			drawbothpgs (canvas);		    
	    }	    
    }
    
	public void drawbothpgs (Canvas canvas)
	{	//draws things that need to be on both pages
		Paint g = new Paint();
		
		int temp_color3 = color3;
		int temp_color4 = color4;
		int temp_color4dark = color4dark;
		int temp_color5 = color5;
		int temp_color6 = color6;
		if (headerpg1)	//on pg1
		{
			switch(num_knobs)
			{				
			case 1:	//only 5 slices
				color3 = Color.WHITE;
			case 2:	//only 4 slices
				color4 = Color.WHITE;
				color4dark = Color.WHITE;
			case 3:	//only 3 slices
				color5 = Color.WHITE;
			case 4:	//only 2 slices
				color6 = Color.WHITE;	//"turns off" label6
			}
		}


		int header_textsize;
		if (pg1header_textsize == 0 || pg2header_textsize == 0)	//finds textsize need for the header on pg1 and pg2
		{
			header_textsize = 70*length/1080;
			header_textsize = header_textsize/2;
		    g.setUnderlineText(true); 
		    g.setFakeBoldText(true);
			g.setTextSize(header_textsize);
			Rect bounds = new Rect();
		    g.getTextBounds(header, 0, header.length(), bounds);
		    
		    int wordlen = (int) g.measureText(header);	//current length of the word
		    int text_height = bounds.height();	//current heigth of the word
			int maxheight = height - x_len_pg2*2/6;	//max height i want
			int maxlen = length - header_x*2;	//max len i want
			while ( text_height < maxheight && wordlen < maxlen)
			{
				header_textsize++;
				g.setTextSize(header_textsize);
			    g.getTextBounds(header, 0, header.length(), bounds);
			    text_height = bounds.height();
			    wordlen = (int) g.measureText(header);
			}
			header_textsize--;
			
			if (headerpg1)//on pg1
				pg1header_textsize = header_textsize;
			else
				pg2header_textsize = header_textsize;
		}
		
		if (headerpg1)//on pg1 and sets the correct text size
			header_textsize = pg1header_textsize;
		else
			header_textsize = pg2header_textsize;	
		
		if (header_overwrite)	//on pg2 and user is typing so display what user typing where header is ie. overwrite the normal header
		{	
			//uses "Investigator View" as the default im measuring with because thats larger than the max word I would allow
			String x = "Investigator View";	
			g.setTextSize(header_textsize);
			Rect bounds1 = new Rect();
		    g.getTextBounds(x, 0, x.length(), bounds1);
		    int wordlen1 = (int) g.measureText(x);
		    int text_height1 = bounds1.height();
			g.setColor(Color.LTGRAY);
			canvas.drawRect(new Rect(header_x, header_y-text_height1, header_x+wordlen1, header_y),g);	//draws the background box
			switch (whichlabel)	//finds the color that corresponds to the label
	 		{
		 	case 1:
		 		g.setColor(color1);
				break;
			case 2:
				g.setColor(color2);
				break;
			case 3:
				g.setColor(color3);
				break;
			case 4:
				g.setColor(color4);
				break;
			case 5:
				g.setColor(color5);
				break;
			case 6:
				g.setColor(color6);
				break;	
	 		}	
			canvas.drawText(header, header_x , header_y , g);
		}
		else
		{	//displays normal header
		    //header text
			g.setColor(Color.BLACK);
		    g.setUnderlineText(true); 
		    g.setFakeBoldText(true);
		    g.setTextSize(header_textsize);
		    canvas.drawText(header, header_x , header_y , g);
			
		    g.setUnderlineText(false); 
		    g.setFakeBoldText(false);
		}
		
	    //6 small square boxes with the 6 colors
	    g.setColor(color1);
	    canvas.drawRect(new Rect(x_colorbox, y_colorbox, x_colorbox+x_len_colorbox, y_colorbox+y_len_colorbox),g);
	    g.setColor(color2);
	    canvas.drawRect(new Rect(x_colorbox, y_colorbox+y_sep_colorbox, x_colorbox+x_len_colorbox, y_colorbox+y_len_colorbox+y_sep_colorbox),g);
	    g.setColor(color3);
	    canvas.drawRect(new Rect(x_colorbox, y_colorbox+2*y_sep_colorbox, x_colorbox+x_len_colorbox, y_colorbox+y_len_colorbox+2*y_sep_colorbox),g);
	    g.setColor(color4);
	    canvas.drawRect(new Rect(x_colorbox, y_colorbox+3*y_sep_colorbox, x_colorbox+x_len_colorbox, y_colorbox+y_len_colorbox+3*y_sep_colorbox),g);
	    g.setColor(color5);
	    canvas.drawRect(new Rect(x_colorbox, y_colorbox+4*y_sep_colorbox, x_colorbox+x_len_colorbox, y_colorbox+y_len_colorbox+4*y_sep_colorbox),g);
	    g.setColor(color6);
	    canvas.drawRect(new Rect(x_colorbox, y_colorbox+5*y_sep_colorbox, x_colorbox+x_len_colorbox, y_colorbox+y_len_colorbox+5*y_sep_colorbox),g);
	    
	    //finds font size for the switch page button
		if (button_textsize == 0)	//only runs at the beginning 
		{
			button_textsize = 250*length/1080;
			button_textsize = button_textsize/2;
			g.setTextSize(button_textsize);
			Rect bounds1 = new Rect();
		    g.getTextBounds(mess_switch_button, 0, mess_switch_button.length(), bounds1);
		    
		    int wordlen1 = (int) g.measureText(mess_switch_button);
		    int text_height1 = bounds1.height();
			int maxheight1 = y_len_pg2 - y_pg2_sep*2;
			int maxlen1 = maxheight1;
			while ( text_height1 < maxheight1 && wordlen1 < maxlen1)
			{
				button_textsize++;
				g.setTextSize(button_textsize);
			    g.getTextBounds(mess_switch_button, 0, mess_switch_button.length(), bounds1);
			    text_height1 = bounds1.height();
			    wordlen1 = (int) g.measureText(mess_switch_button);
			}
			button_textsize--;
		}

	    //this is pg2 button also called the switch pages button
	    g.setColor(Color.BLACK);
	    g.setTextSize(button_textsize);
	    canvas.drawRect(new Rect(x_pg2,y_pg2 , x_pg2+x_len_pg2, y_pg2+y_len_pg2),g);
	    g.setColor(Color.WHITE);
	    canvas.drawText(mess_switch_button,x_pg2+x_pg2_sep ,y_pg2+y_len_pg2-y_pg2_sep,g );	    
	    
	    //sets the colors back to normal
		color3 = temp_color3;
		color4 = temp_color4;
		color4dark = temp_color4dark;
		color5 = temp_color5;
		color6 = temp_color6;	
	}
	
	public void page2 (Canvas canvas)
	{	//on page 2
		globcanvas = canvas;
	    Paint g = new Paint();
	    drawbothpgs (canvas);
	    
		y_sep_pertext = y_sep_colorbox;
		x_pertext = length - x_len_colorbox*7/6 - x_len_colorbox/3;
		
		if (percent_textsize == 0)	//finds font size for the percentages
		{
			//finding approriate text size for percent text		
			Rect bounds = new Rect();
			area1 = "100%";	//use 100% as the default for measuring font size since thats the largest the percent can be
			percent_textsize = 70*length/1080;
			percent_textsize = percent_textsize/2;
			g.setTextSize(percent_textsize);
		    g.getTextBounds(area1, 0, area1.length(), bounds);
		    int wordlen = (int) g.measureText(area1);
		    int text_height = bounds.height();
			int maxheight = y_len_colorbox - 2*y_len_colorbox/6;
			int maxlen = x_len_colorbox*7/6 ;
			while ( text_height < maxheight && wordlen < maxlen)
			{
				percent_textsize++;
				g.setTextSize(percent_textsize);
			    g.getTextBounds(area1, 0, area1.length(), bounds);
			    text_height = bounds.height();
			    wordlen = (int) g.measureText(area1);
			}
			percent_textsize--;
			g.setTextSize(percent_textsize);
		    g.getTextBounds(area1, 0, area1.length(), bounds);
		    text_height = bounds.height();
			pertext_height = text_height;
		}
	    
		//aligns text in the center  (with respect to y-axis)
		y_pertext = y_len_colorbox - pertext_height;
		y_pertext = y_pertext/2;
		y_pertext = y_colorbox + y_len_colorbox - y_pertext;		
	    calculateArea();
	    //text of percentages
	    g.setTextSize(percent_textsize);
		g.setColor (color1);
	    canvas.drawText(area1, x_pertext, y_pertext,g);
		g.setColor (color2);
	    canvas.drawText(area2, x_pertext, y_pertext+y_sep_pertext,g);
		g.setColor (color3);
	    canvas.drawText(area3, x_pertext, y_pertext+2*y_sep_pertext,g);
		g.setColor (color4dark);
	    canvas.drawText(area4, x_pertext, y_pertext+3*y_sep_pertext,g);	
		g.setColor (color5);
		//g.setColor (Color.GRAY);
	    canvas.drawText(area5, x_pertext, y_pertext+4*y_sep_pertext,g);
		g.setColor (color6);
		//g.setColor (Color.GRAY);
	    canvas.drawText(area6, x_pertext, y_pertext+5*y_sep_pertext,g);
	    
	    //------------------
	    int buff = x_len_colorbox/5;
		if (buff < 10*length/1080)
			buff = 10*length/1080;
		int temp_x = x_colorbox + x_len_colorbox + buff;
		
		if (pg2label_textsize == 0)
		{
			//finding approriate text size for label names
			String longest_label_poss = "Short Label 1";
			Rect bounds1 = new Rect();
			pg2label_textsize = 80*length/1080;
			pg2label_textsize = pg2label_textsize/2;
			g.setTextSize(pg2label_textsize);
		    g.getTextBounds(longest_label_poss, 0, longest_label_poss.length(), bounds1);
		    int wordlen1 = (int) g.measureText(longest_label_poss);
		    int text_height1 = bounds1.height();
		    int maxheight1 = x_len_colorbox - 2*buff;
			int maxlen1 = length - (length - x_pertext + buff) - temp_x;
			while ( text_height1 < maxheight1 && wordlen1 < maxlen1)
			{
				pg2label_textsize++;
				g.setTextSize(pg2label_textsize);
			    g.getTextBounds(longest_label_poss, 0, longest_label_poss.length(), bounds1);
			    text_height1 = bounds1.height();
			    wordlen1 = (int) g.measureText(longest_label_poss);
			}
			pg2label_textsize--;
			g.setTextSize(pg2label_textsize);
		    g.getTextBounds(longest_label_poss, 0, longest_label_poss.length(), bounds1);
		    text_height1 = bounds1.height();
			labeltext_height = text_height1;
			wordlen1 = (int) g.measureText(longest_label_poss);
			
			//add buttons for the 6 label inputs for the keyboard
			int temp_y1 = y_len_colorbox - labeltext_height;
			temp_y1 = temp_y1/2;
			temp_y1 = y_colorbox + y_len_colorbox - temp_y1;
			label1_but = new Button (temp_x, y_colorbox, wordlen1, y_len_colorbox);
			label2_but = new Button (temp_x, y_colorbox+y_sep_colorbox , wordlen1, y_len_colorbox);
			label3_but = new Button (temp_x, y_colorbox+y_sep_colorbox*2 , wordlen1, y_len_colorbox);
			label4_but = new Button (temp_x, y_colorbox+y_sep_colorbox*3 , wordlen1, y_len_colorbox);
			label5_but = new Button (temp_x, y_colorbox+y_sep_colorbox*4 , wordlen1, y_len_colorbox);
			label6_but = new Button (temp_x, y_colorbox+y_sep_colorbox*5 , wordlen1, y_len_colorbox);
		 	allButtons.add(label1_but);
		 	allButtons.add(label2_but);
		 	allButtons.add(label3_but);
		 	allButtons.add(label4_but);
		 	allButtons.add(label5_but);
		 	allButtons.add(label6_but);
		 	
		 	//initialize the box buttons since this if loop runs once when pg2 is first viewed
		 	box3 = new Button(x_colorbox, y_colorbox+2*y_sep_colorbox, x_len_colorbox, y_len_colorbox);
		 	box4 = new Button(x_colorbox, y_colorbox+3*y_sep_colorbox, x_len_colorbox, y_len_colorbox);
		 	box5 = new Button(x_colorbox, y_colorbox+4*y_sep_colorbox, x_len_colorbox, y_len_colorbox);
		 	box6 = new Button(x_colorbox, y_colorbox+5*y_sep_colorbox, x_len_colorbox, y_len_colorbox);
		 	boxButtons.add(box3);
		 	boxButtons.add(box4);
		 	boxButtons.add(box5);
		 	boxButtons.add(box6);
		}
		
		//aligns the label names in the center (with respect to y-axis)
		int temp_y = y_len_colorbox - labeltext_height;
		temp_y = temp_y/2;
		temp_y = y_colorbox + y_len_colorbox - temp_y;
		
		//draws rectangular background where label names go
		g.setTextSize (pg2label_textsize);
	 	int wordlen1 = (int) g.measureText("Short Label 1");
		g.setColor (Color.LTGRAY);
	 	canvas.drawRect(new Rect(temp_x, y_colorbox, temp_x+wordlen1, y_colorbox+y_len_colorbox),g);
		g.setColor (Color.LTGRAY);
		canvas.drawRect(new Rect(temp_x, y_colorbox+y_sep_colorbox, temp_x+wordlen1, y_colorbox+y_len_colorbox+y_sep_colorbox),g);
		g.setColor (Color.LTGRAY);
		canvas.drawRect(new Rect(temp_x, y_colorbox+y_sep_colorbox*2, temp_x+wordlen1, y_colorbox+y_len_colorbox+y_sep_colorbox*2),g);
		g.setColor (Color.LTGRAY);
		canvas.drawRect(new Rect(temp_x, y_colorbox+y_sep_colorbox*3, temp_x+wordlen1, y_colorbox+y_len_colorbox+y_sep_colorbox*3),g);
		g.setColor (Color.LTGRAY);
		canvas.drawRect(new Rect(temp_x, y_colorbox+y_sep_colorbox*4, temp_x+wordlen1, y_colorbox+y_len_colorbox+y_sep_colorbox*4),g);
		g.setColor (Color.LTGRAY);
		canvas.drawRect(new Rect(temp_x, y_colorbox+y_sep_colorbox*5, temp_x+wordlen1, y_colorbox+y_len_colorbox+y_sep_colorbox*5),g);

	    //label names
		g.setTextSize (pg2label_textsize);
		g.setColor (color1);
		canvas.drawText(label1, temp_x , temp_y , g);
		g.setColor (color2);
		canvas.drawText(label2, temp_x , temp_y+y_sep_colorbox , g);
		g.setColor (color3);
		canvas.drawText(label3, temp_x , temp_y+y_sep_colorbox*2 , g);
		g.setColor (color4);
		canvas.drawText(label4, temp_x , temp_y+y_sep_colorbox*3 , g);
		g.setColor (color5);
		canvas.drawText(label5, temp_x , temp_y+y_sep_colorbox*4 , g);
		g.setColor (color6);
		canvas.drawText(label6, temp_x , temp_y+y_sep_colorbox*5 , g);
		
		//box buttons "X"
		g.setTextSize (pg2label_textsize);
		g.setFakeBoldText(true);
		g.setColor(Color.BLACK);
	    wordlen1 = (int) g.measureText("X");	
	    int xpos = (x_len_colorbox-wordlen1)/2;	//so that "X" is in aligned in center of colorbox
	    
	    for (int i=0; i<num_knobs+1; i++)
	    {
			canvas.drawText("X", x_colorbox+xpos , temp_y+y_sep_colorbox*i , g);
	    }
	    
	    //hide percents button
	 	int x =  (int) hide_percents.getxCoord();
	 	int y = (int) hide_percents.getyCoord();
	 	int xlen = (int) hide_percents.getLength();
	 	int ylen = (int) hide_percents.getHeight();	 	
	    g.setColor(Color.RED);
	    g.setTextSize(hide_textsize);
	    canvas.drawRect(new Rect(x,y , x+xlen, y+ylen),g);
	    
	 	String hide_mess = "Hide";
	 	String hide_mess2 = "%";
	 	hide_textsize = pg1label_textsize;
		Rect bounds = new Rect();
		g.setTextSize(pg1label_textsize);
	    g.getTextBounds(hide_mess, 0, hide_mess.length(), bounds);
	    int text_height = bounds.height();	//current height
	    int text_len = (int) g.measureText(hide_mess);	//current length of the word
	    int space = (ylen - y_pg2_sep - text_height*2)/2;
	    y = y + ylen - y_pg2_sep - space - text_height;
	    x = x + (xlen - text_len)/2;
	    
	    g.setColor(Color.WHITE);
	    canvas.drawText(hide_mess , x , y , g );
	    
	    y = y + text_height;
	    text_len = (int) g.measureText(hide_mess2);
	    x =  (int) hide_percents.getxCoord();
	    x = x + (xlen - text_len)/2;
	    canvas.drawText(hide_mess2 , x , y+y_pg2_sep , g );

	}
	
	//changes variables for pg2
	public void reset_pg2 ()
	{
    	onpg2 = true;
    	
    	header = "Investigator View";
    	headerpg1 = false;

    	x_pg2 = 0;
    	
    	mess_switch_button = "<";
    	pg2_button.setButton(x_pg2, y_pg2, x_len_pg2, y_len_pg2);
    	
 		int temp2 = y_len_pg2/4;
    	y_colorbox = y_len_pg2 + y_len_pg2/2;	
    	temp = height - y_len_pg2 - y_len_pg2/2 - temp2; //space left to work with
 		y_sep_colorbox = temp/6;
    	x_len_colorbox = 4*y_sep_colorbox / 7;
      	y_len_colorbox = x_len_colorbox;
    	x_colorbox = size_knob;
	}
	
	//resets variable for pg1
	public void reset_pg1 ()
	{
		onpg2 = false;
		
		header = "Participant View";
		headerpg1 = true;

		x_pg2 = length - x_len_pg2;	//starting x coord
			 
    	mess_switch_button = ">";
    	pg2_button.setButton(x_pg2, y_pg2, x_len_pg2, y_len_pg2);
    	
 		temp = length - size_knob*2;
		y_colorbox = y_cir + y_len_cir + size_knob + size_knob/2;			
 		
 		
    	//resets colorbox position
 		int temp2 = 10*length/1080;
 		y_colorbox += temp2;	//buffer at the bottom
 		temp = height - y_colorbox;	//space left to work with
 		y_sep_colorbox = temp/6; //y value that separates each colorbox
    	x_len_colorbox = 4*y_sep_colorbox / 5;
      	y_len_colorbox = x_len_colorbox;
    	x_colorbox = size_knob;
 		y_colorbox -= temp2;	//a buffer at the bottom	
 	}
	 
    private void calculateArea() {
    	
    	area1 = "0";
    	area2 = "0";
    	area3 = "0";
    	area4 = "0";
    	area5 = "0";
    	area6 = "0";

    	double area_1=0, area_2=0, area_3=0, area_4=0, area_5=0, area_6=0;
    	
    	switch (num_knobs)	//gets percentages depending on how many knobs active (ie. slices active)
    	{
    	case 1:
    		area_2 = Math.abs(Math.round(slice2.getStartAng()/360*1000)/10.0);
    		area_1 = Math.abs(100-area_2);
    		break;
    	case 2:
    		area_3 = Math.abs(Math.round(slice3.getStartAng()/360*1000)/10.0);        	
        	area_2 = Math.abs(Math.round((Math.abs(slice2.getStartAng())-Math.abs(slice3.getStartAng()))/360*1000)/10.0);    		
    		area_1 = Math.abs(100-area_3-area_2);
    		break;
    	case 3:
    		area_4 = Math.abs(Math.round(slice4.getStartAng()/360*1000)/10.0);        	
        	area_3 = Math.abs(Math.round((Math.abs(slice3.getStartAng())-Math.abs(slice4.getStartAng()))/360*1000)/10.0);    		
    		area_2 = Math.abs(Math.round((Math.abs(slice2.getStartAng())-Math.abs(slice3.getStartAng()))/360*1000)/10.0);
    		area_1 = Math.abs(100-area_4-area_2-area_3);
    		break;
    	case 4:
    		area_5 = Math.abs(Math.round(slice5.getStartAng()/360*1000)/10.0);        	
        	area_4 = Math.abs(Math.round((Math.abs(slice4.getStartAng())-Math.abs(slice5.getStartAng()))/360*1000)/10.0);    		
        	area_3 = Math.abs(Math.round((Math.abs(slice3.getStartAng())-Math.abs(slice4.getStartAng()))/360*1000)/10.0);
        	area_2 = Math.abs(Math.round((Math.abs(slice2.getStartAng())-Math.abs(slice3.getStartAng()))/360*1000)/10.0);        	
    		area_1 = Math.abs(100-area_5-area_4-area_3-area_2);
    		break;
    	case 5:
    		area_6 = Math.abs(Math.round(slice6.getStartAng()/360*1000)/10.0);        	
        	area_5 = Math.abs(Math.round((Math.abs(slice5.getStartAng())-Math.abs(slice6.getStartAng()))/360*1000)/10.0);        	
        	area_4 = Math.abs(Math.round((Math.abs(slice4.getStartAng())-Math.abs(slice5.getStartAng()))/360*1000)/10.0);    		
        	area_3 = Math.abs(Math.round((Math.abs(slice3.getStartAng())-Math.abs(slice4.getStartAng()))/360*1000)/10.0);
        	area_2 = Math.abs(Math.round((Math.abs(slice2.getStartAng())-Math.abs(slice3.getStartAng()))/360*1000)/10.0);        
    		area_1 = Math.abs(100-area_6-area_5-area_4-area_3-area_2);
    		break;
    	}
    	//round to 1 decimal place
    	area_6 = Math.round(area_6*10)/10.0;
    	area_5 = Math.round(area_5*10)/10.0;
    	area_4 = Math.round(area_4*10)/10.0;
    	area_3 = Math.round(area_3*10)/10.0;
    	area_2 = Math.round(area_2*10)/10.0;
    	area_1 = Math.round(area_1*10)/10.0;
    	
		area6 = Double.toString(area_6);
		area5 = Double.toString(area_5);
		area4 = Double.toString(area_4);
		area3 = Double.toString(area_3);
		area2 = Double.toString(area_2);
		area1 = Double.toString(area_1);
		
		area1+="%";
		area2+="%";
		area3+="%";
		area4+="%";
		area5+="%";
		area6+="%";	

	}

	public void changeSliceArc(Slice slicea, Knob knob1, int whichknob)
    {
		slicea.setStartAng(knob1.getArcDeg());
		slicea.setSweepAng(slicea.getStartAng());
		
    }
}
