import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ButtonModule} from 'primeng/button';
import { PasswordRecoveryRoutingModule } from './password-recovery-routing.module';
import { ResetComponentComponent } from './components/reset-component/reset-component.component';
import { LoadingImageModule } from '@app/common/components/loading-image/loading-image.module';
import { MessageModule } from 'primeng/message';
import { PrimengModule } from '@app/common/modules/primeng-modules';



@NgModule({
  declarations: [
    ResetComponentComponent
  ],
  imports: [
    CommonModule,    
    ButtonModule,
    PasswordRecoveryRoutingModule,
    LoadingImageModule,
    MessageModule,
    PrimengModule,
  ]
})
export class PasswordRecoveryModule { }
