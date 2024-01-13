import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ApplicationCustomsStepFourComponent } from './application-customs-step-four.component';
import { ApplicationCustomsStepTwoComponent } from '../application-customs-step-two/application-customs-step-two.component';

const routes: Routes = [
  {
    path: ':oid',
    component: ApplicationCustomsStepFourComponent
  },
  {
    path: 'application-customs-step-two/:oid',
    component: ApplicationCustomsStepTwoComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationCustomsStepFourRoutingModule { }
