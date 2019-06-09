#include <stdio.h> 
#include <stdlib.h> 
#include <stdbool.h> 

// MAX DIST SHOULD BE CAR LENGTH 
#define MAX_DIST 22

bool f_dist; 
bool l_dist; 
bool r_dist; 
bool b_dist; 

//////////////////////////////////////////////////
// STRUCTS FOR COORDS AND SCAN PTS
//////////////////////////////////////////////////
struct Coordinates{
    int X; 
    int Y; 
};

// Coordinates Curr, Next, Prev; 

// struct to store the known obst data 
struct Obst{
    Coordinates North, South, East, West;  
};

// Struct for various attributes of a scan point 
struct ScanPt{
    //  Cords of current, n
    Coordinates Source, Goal, Igoal;
    //  Cords of Reach pts
    Coordinates ReachPtCords [4]; 
    //  Stores scan point # of each Rpt
    int ScanPtNumsOfReachPts[4];  
    //  number to id a scan point 
    int ScanPtNum; 
    //  number to id a goal of current scan point 
    int GoalPtNum; 
    //  id access of each reach point 
    int ReachPtAccessibility[10][10];
    //  total # of reach points for a scan point 
    int NumOfReachPts; 
    //  indicate if scan point has been visited 
    int Visited; 
    //  indicates if scan point can be reached 
    int IsScanPointReachable; 
    //  identifies the obstacle which makes this point unreachable 
    int ObstacleNo; 
    //  alternate goal pt in case of known obstacles 
    int AltGoalPtNum; 
    //  weight for every ScanPoint, Ones visited more times get more weight 
    int Weightage; 
};

//////////////////////////////////////////////////
// INITIAL SETUP 
// for all scan pts, calcs x and y coords, goal point, reach pts. 
//////////////////////////////////////////////////
void setup() {
    f_dist = false;
    l_dist = false; 
    r_dist = false; 
    b_dist = false; 
    ScanPtNumber = 1; 
    iCounter = 1; 
    NumXScanPts = ceil(FieldWidth/VehicleWidth)+1; 
    NumYScanPts = ceil(FieldLength/[sensorRange/2])+1; 
    XScanRange = FieldWidth/(NumXScanPts-1); 
    YScanRange = FieldLength/(NumYScanPts-1); 
    TotNumScanPts = NumXScanPts * NumYScanPts; 
    Temp = NumYScanPts; 
    for(iCountX = 0; iCountX < NumXScanPts; iCountX++){
        for(iCountY = 0; iCountY < NumXScanPts; iCountY++){
            Xcord = (iCountX*XScanRange); 
            Ycord = (iCountY*YScanRange); 
            if((iCountX%2)!= 0){
                ScanPtNumber = iCounter + NumYScanPts - (iCountY*2)-1; 
            }else{
                ScanPtNumber = iCounter; 
            }
            ScanPt[ScanPtNumber].ScanPtNum = ScanPtNumber; 
            ScanPt[ScanPtNumber].Source.X = Xcord = Curr.X;
            ScanPt[ScanPtNumber].Source.Y = Ycord = Curr.Y;  
            iCounter++; 
            NumOfReachPts = 0; 
        }
    }
//    find scan pt #'s with those coords, 
//    assign these scan point #'s as the reach pts
    for(icount = 1; icount < TotNumScanPts; icount++){
        ScanPt[ScanPtNumber].Goal.X = ScanPt[ScanPtNumber+1].Source.X; 
        ScanPt[ScanPtNumber].Goal.Y = ScanPt[ScanPtNumber+1].Source.Y; 
    }
}

//////////////////////////////////////////////////
// MOVE VEHICLE 
//////////////////////////////////////////////////
void MoveVehicle(ScanPt startPt, ScanPt endPt, ScanPt prevPt){
    if(endPt.X == prevPt.X){
        Direction = straight; 
    }else{
        val = ((endPt.Y-prevPt.Y)/(endPt.X-prevPt.X)); 
        if(startPt.ScanPtNum > endPt.ScanPtNum){
            val = -val; 
        }
        if(val == 0){
            Direction = straight; 
        }
        else if(val>0){
            Direction = right; 
        }
        else if(val<0){
            Direction = left; 
        }
    }
    mechanicalMove(Direction, startPt, endPt); 
}

//////////////////////////////////////////////////
// DETECT OBSTACLES 
// Gets input from sensors and calculates obst position w/resp to scan pts
// return 1 if obst exists between current scan pt and its goal pt 
// return 0 if obst DNE between current scan pt and its goal pt 
//////////////////////////////////////////////////
bool ObstDet(ScanPt Curr, ScanPt Next){
    read_sensors(); 
    // if sensors dist is < car length, throw a flag for that sensor 
    if((fmax || lmax || bmax || rmax) == true){
        // obstacle detected 
        if(Curr.Source.X == Next.Source.X){
            if(fmax){
                return 1; 
            }else{
                return 0; 
            }
        }else{
            // slope of movement to next pt
            val = ((Next.Source.Y-Curr.Source.Y)/(Next.Source.X-Curr.Source.X)); 
            if(val == 0){
                
            }   
        }
    }else{
        return 0; 
    }

}

//////////////////////////////////////////////////
// READ SENSORS
//////////////////////////////////////////////////
void read_sensors(){
    f_dist = GPIO.input(); 
    l_dist = GPIO.input(); 
    r_dist = GPIO.input(); 
    b_dist = GPIO.input(); 
    // detect obstacles on sensors 
    if(f_dist <= MAX_DIST){
        fmax = true; 
    } 
    if(l_dist <= MAX_DIST){
        lmax = true; 
    }
    if(r_dist <= MAX_DIST){
        rmax = true; 
    }
    if(b_dist <= MAX_DIST){
        bmax = true; 
    } 
}

//////////////////////////////////////////////////
// LOCAL PATH NAVIGATION 
//////////////////////////////////////////////////
void LocalPathNav(SourceScanPtNum, GoalPtNum){
/*  create two stacks
 *   store the source scan point number
 *   store the goal scan point number in as the first element of both the stacks 
 *   find the alternate goals in the left local path
 *   store each of these alternate goals in the left stack
 *   check if reach point of alternate goal is same as the source scan point
 *   if so, left local path is completed
 *   repeat procedure to create a right local path
 *   Based upon the shorter of the two an optimum path is selected
 *   Pop the optimum stack's scan point nums and call move vehicle function 
 *    if a newly discovered obstacle is in the path of the local path, then call the local path function
*    again with the new source and goal point numbers; 
*     continue the recursive process until the original goal is reached
 */
}

void loop() {
  // put your main code here, to run repeatedly:

}