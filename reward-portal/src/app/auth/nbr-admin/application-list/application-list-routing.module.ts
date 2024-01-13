import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ApplicationDetailsStepOneComponent} from './component/application-details-step-one/application-details-step-one.component';
import {ApplicationDetailsStepTwoComponent} from './component/application-details-step-two/application-details-step-two.component';
import {ApplicationDetailsStepThreeComponent} from './component/application-details-step-three/application-details-step-three.component';

import { ApplicationListComponent } from './application-list.component';
import { ApplicationDetailsStepTwoAcceptedComponent } from './component/application-details-step-two-accepted/application-details-step-two-accepted.component';
import { ArchiveComponent } from './component/archive/archive.component';

const routes: Routes = [
    {
        path: '',
        component: ApplicationListComponent
    },  
    {
        path: 'nbr-admin-step-one/:oid',
        component: ApplicationDetailsStepOneComponent
    },
    {
        path: 'nbr-admin-step-two/:oid',
        component: ApplicationDetailsStepTwoComponent
    }, {
        path: 'nbr-admin-step-two-accepted/:oid',
        component: ApplicationDetailsStepTwoAcceptedComponent
    },
    {
        path: 'nbr-admin-step-three/:oid',
        component: ApplicationDetailsStepThreeComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ApplicationListRoutingModule {
}
