import { AnagPage } from './app.po';

describe('anag App', () => {
  let page: AnagPage;

  beforeEach(() => {
    page = new AnagPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
