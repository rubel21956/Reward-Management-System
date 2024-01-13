import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {
  ApplicationDetailsStepThreeComponent
} from '@app/auth/nbr-admin/application-details-step-three/application-details-step-three.component';

const routes: Routes = [
  {
    path: '',
    component: ApplicationDetailsStepThreeComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationDetailsStepThreeRoutingModule { }
