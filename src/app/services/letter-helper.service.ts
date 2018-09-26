import { Injectable } from '@angular/core';

@Injectable()
export class LetterHelperService {

  constructor() { }

  // https://github.com/angular/angular/issues/10423
  trackByIndex(index: number, value: number) {
    return index;
  }

  moveToNext($event) {
    let nextInput = $event.srcElement.nextElementSibling;
    if ($event.target.value.length > 0 && nextInput != null) {
      nextInput.focus()
    }
  }

  letterArrayToString(array: string[]) {
    // NB: array.map ignores completely null/unset values!
    // We have to use an old-style for.
    const adjustedArray = Array<string>(array.length);
    for (let i = 0; i < array.length; i++) {
      const x = array[i];
      adjustedArray[i] = (x == null || x == undefined || x == " " || x == "") ? '?' : x;
    }
    return adjustedArray.join('');
  }

}
