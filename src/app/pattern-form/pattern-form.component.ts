import {Component, OnInit} from '@angular/core';
import {Http} from "@angular/http";
import {LetterHelperService} from "../services/letter-helper.service";

@Component({
  selector: 'pattern-form',
  templateUrl: './pattern-form.component.html',
  styleUrls: ['./pattern-form.component.css']
})
export class PatternFormComponent implements OnInit {
  public totalLength = 6;
  public lengthString = "";
  public knownLetters: string[] = [];
  public words: string[] = null;

  constructor(
    private http: Http,
    public letterHelperService: LetterHelperService
  ) { }

  ngOnInit() {
    this.lengthChange();
  }

  range(start, end) {
    return Array
      .from(Array(1 + end - start).keys())
      .map(n => start + n);
  }

  lengthChange() {
    this.knownLetters.length = this.totalLength;
    // array.forEach will skip over the null values!! So this will never work
    // this.knownLetters.forEach((value, index) => {
    //   if (value == undefined) {
    //     this.knownLetters[index] = "";
    //   }
    // });
    for (let i = 0; i < this.knownLetters.length; i++) {
      if (this.knownLetters[i] == undefined) {
        this.knownLetters[i] = "";
      }
    }
  }

  updateLengthFromString() {
    if (this.lengthString.trim().length > 0) {
      this.http.get('/api/parseLengthString?lengthString=' + this.lengthString.trim())
        .map(res => res.json())
        .subscribe(json => {
          this.totalLength = json["totalLength"];
          this.lengthChange();
        });
    }
  }

  match() {
    const known = this.letterHelperService.letterArrayToString(this.knownLetters);
    console.log(`knownLetters = ${JSON.stringify(this.knownLetters)}, known = ${known}`);

    this.http.get('/api/knownLetterMatches?known=' + known)
      .map(res => res.json())
      .subscribe( json => {
        this.words = (json as Array<string>).sort( (a, b) => a.toLowerCase().localeCompare(b.toLowerCase()));
      });
  }

  clear() {
    this.totalLength = 6;
    this.lengthString = "";
    this.knownLetters = [];
    this.lengthChange();
  }
}
