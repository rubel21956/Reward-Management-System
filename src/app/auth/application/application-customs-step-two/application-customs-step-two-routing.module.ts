import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {
    ApplicationCustomsStepTwoComponent
} from '@app/auth/application/application-customs-step-two/application-customs-step-two.component';
import {ApplicationCustomsStepOneComponent} from '../application-customs-step-one/application-customs-step-one.component';

const routes: Routes = [
    {
        path: ':oid',
        component: ApplicationCustomsStepTwoComponent
    },
    {
        path: 'application-customs-step-one/:oid',
        component: ApplicationCustomsStepOneComponent
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ApplicationCustomsStepTwoRoutingModule {
}
