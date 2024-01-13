import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ResetComponentComponent } from './components/reset-component/reset-component.component';

const routes: Routes = [
  {path: '', component: ResetComponentComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PasswordRecoveryRoutingModule { }
