import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {
  ApplicationCustomsStepFiveComponent
} from '@app/auth/application/application-customs-step-five/application-customs-step-five.component';

const routes: Routes = [
  {
    path: ':oid',
    component: ApplicationCustomsStepFiveComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationCustomsStepFiveRoutingModule { }
