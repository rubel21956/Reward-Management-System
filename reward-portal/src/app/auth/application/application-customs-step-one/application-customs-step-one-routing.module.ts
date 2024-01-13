import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ApplicationCustomsListComponent } from '../application-customs-list/application-customs-list.component';
import { ApplicationCustomsStepOneComponent } from './application-customs-step-one.component';

const routes: Routes = [
  {
    path: '',
    component: ApplicationCustomsStepOneComponent
  },
  // {
  //   path: 'application-customs-list',
  //   component: ApplicationCustomsListComponent
  // }
  {
    path: ':oid',
    component: ApplicationCustomsStepOneComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApplicationCustomsStepOneRoutingModule { }
