import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArchiveComponent } from './archive/archive.component';
import { RejectedApplicationComponent } from './rejected-application/rejected-application.component';

const routes: Routes = [
  {
    path: '',
    component: ArchiveComponent
  },
  {
    path: 'rejectedApplication', pathMatch: 'full',
    component: RejectedApplicationComponent
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class OperatorArchiveRoutingModule { }
