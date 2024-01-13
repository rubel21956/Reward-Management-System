import { NgModule } from '@angular/core';

import { BDCurrency } from './bd-currency-format.pipe';
import { DateFormat } from './date-format.pipe';

@NgModule({
    imports: [],
    declarations: [
        BDCurrency,
        DateFormat
    ],
    exports: [
        BDCurrency,
        DateFormat
    ]
})
export class AppPipesModule { }
