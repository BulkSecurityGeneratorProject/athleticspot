import {TrackerSource} from "./tracker-source";

export class SportActivityModel {

    duration: String;
    unit = "km";
    time = "0";


    id: String;
    externalId: String;
    firstAndLastName: String;
    trackerSource: TrackerSource;
    sportActivityType: String;
    title: String;
    description: String;
    distance: Number;
    units: String;
    movingTime: Number;
    elapsedTime: Number;
    startDate: String;  //Sting type is set on pourpose in order to not use js Date
    averageSpeed: Number;
    maxSpeed: Number;
    averageTemp: Number;
    calories: Number;
    coordinates: any;
    formattedPace: String;
    activityUrl: String;

    public static resolveSportActivityTypeDescription(sportActivityType: String, typesTranslation: any) {
        return  typesTranslation.manual.type[sportActivityType + ""]
    }

    public isExternalTracker(){
        return TrackerSource.MANUAL != this.trackerSource;

    }
}
