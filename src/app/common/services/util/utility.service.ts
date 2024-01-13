import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilityService {

  constructor() { }
  getBanglaDigitFromEnglish(inputString: string) {
    let finalEnlishToBanglaNumber = { '0': '০', '1': '১', '2': '২', '3': '৩', '4': '৪', '5': '৫', '6': '৬', '7': '৭', '8': '৮', '9': '৯' };
    for (let x in finalEnlishToBanglaNumber) {
        inputString = inputString.toString().replace(new RegExp(x, 'g'), finalEnlishToBanglaNumber[x]);
    }
    return inputString;
}

getEnglishDigitFromBangla(inputString: string) {
    let finalEnlishToBanglaNumber = { '০': '0', '১': '1', '২': '2', '৩': '3', '৪': '4', '৫': '5', '৬': '6', '৭': '7', '৮': '8', '৯': '9' };
    for (let x in finalEnlishToBanglaNumber) {
        inputString = inputString.replace(new RegExp(x, 'g'), finalEnlishToBanglaNumber[x]);
    }
    return inputString;
}

convertEnglishToBangla(amountList) {
  let finalEnlishToBanglaNumber = { '0': '০', '1': '১', '2': '২', '3': '৩', '4': '৪', '5': '৫', '6': '৬', '7': '৭', '8': '৮', '9': '৯' };
  amountList.forEach(function (value) {
          for (let x in finalEnlishToBanglaNumber) {
              value.appliedRewardAmount = value.appliedRewardAmount.toString().replace(new RegExp(x, 'g'), finalEnlishToBanglaNumber[x]);
          }
  });
  
}
}
