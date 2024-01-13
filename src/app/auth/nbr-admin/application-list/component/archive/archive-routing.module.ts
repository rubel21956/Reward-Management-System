import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArchiveComponent } from './archive.component';
import { ApplicationDetailsStepOneComponent } from '../application-details-step-one/application-details-step-one.component';
import { ApplicationDetailsStepThreeComponent } from '../application-details-step-three/application-details-step-three.component';
import { ApplicationDetailsStepTwoAcceptedComponent } from '../application-details-step-two-accepted/application-details-step-two-accepted.component';
import { ApplicationDetailsStepTwoComponent } from '../application-details-step-two/application-details-step-two.component';

const routes: Routes = [
  {path:'', component:ArchiveComponent},

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
export class ArchiveRoutingModule { }
