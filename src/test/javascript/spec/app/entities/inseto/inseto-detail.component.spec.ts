import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Mamiferos2020TestModule } from '../../../test.module';
import { InsetoDetailComponent } from 'app/entities/inseto/inseto-detail.component';
import { Inseto } from 'app/shared/model/inseto.model';

describe('Component Tests', () => {
  describe('Inseto Management Detail Component', () => {
    let comp: InsetoDetailComponent;
    let fixture: ComponentFixture<InsetoDetailComponent>;
    const route = ({ data: of({ inseto: new Inseto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [InsetoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InsetoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InsetoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load inseto on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inseto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
