import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ApplicationCustomsListComponent} from '@app/auth/application/application-customs-list/application-customs-list.component';
import {ApplicationCustomsStepOneComponent} from '../application-customs-step-one/application-customs-step-one.component';
import { UpdateApplicationCustomsStepOneComponent } from '../component/update-application-customs-step-one/update-application-customs-step-one.component';
import { ViewApplicationCustomsStepOneComponent } from '../component/view-application-customs-step-one/view-application-customs-step-one.component';

const routes: Routes = [
    {
        path: '',
        component: ApplicationCustomsListComponent
    },
    // {
    //     path: 'application-customs-step-one/:oid',
    //     component: ApplicationCustomsStepOneComponent
    // }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ApplicationCustomsListRoutingModule {
}
