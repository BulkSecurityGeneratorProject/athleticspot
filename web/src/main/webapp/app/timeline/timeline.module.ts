import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {AthleticspotSharedModule} from "../shared/shared.module";
import {RouterModule} from "@angular/router";
import {NgDatepickerModule} from 'ng2-datepicker';
import {ReactiveFormsModule} from "@angular/forms";
import {Ng2DropdownModule} from 'ng2-material-dropdown';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ActivitiesComponent} from "./activities/activities.component";
import {timelineState} from "./timeline.route";
import {TrackerDataservice} from "./activities/tracker-dataservice.service";
import {InfiniteScrollModule} from "ngx-infinite-scroll";
import {CommonModule} from "@angular/common";
import {ActivitiesDataservice} from "../shared/activites/activities.dataservice";
import {AgmCoreModule} from "@agm/core"
import {NgbTimeStringAdapter} from "./activities/timeticker-string-adapter.service";
import {StravaComponent} from "./strava/strava.component";


@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        AthleticspotSharedModule,
        NgDatepickerModule,
        BrowserAnimationsModule,
        Ng2DropdownModule,
        AgmCoreModule.forRoot(
            {
                apiKey: "AIzaSyAX3uLsEzkQIef16sJvkB5oJzQGR2Ej41k"
            }
        ),
        InfiniteScrollModule,
        RouterModule.forRoot(timelineState, {useHash: true}),
    ],
    declarations: [
        ActivitiesComponent,
        StravaComponent
    ],
    entryComponents: [ActivitiesComponent],
    providers: [
        ActivitiesDataservice,
        TrackerDataservice,
        NgbTimeStringAdapter
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TimelineModule {
}
