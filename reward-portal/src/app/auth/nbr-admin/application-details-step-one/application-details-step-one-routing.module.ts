import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ApplicationDetailsStepOneComponent} from '@app/auth/nbr-admin/application-details-step-one/application-details-step-one.component';
import {ApplicationDetailsStepTwoComponent} from '@app/auth/nbr-admin/application-details-step-two/application-details-step-two.component';

const routes: Routes = [
    {
        path: '',
        component: ApplicationDetailsStepOneComponent
    },
    {
        path: 'nbr-admin-step-two/:oid',
        component: ApplicationDetailsStepTwoComponent
    }
]

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ApplicationDetailsStepOneRoutingModule {
}
