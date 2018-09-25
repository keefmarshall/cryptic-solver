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
    return array
      .map(x => (x == null || x == " " || x == "") ? '?' : x)
      .join('');
  }

}
