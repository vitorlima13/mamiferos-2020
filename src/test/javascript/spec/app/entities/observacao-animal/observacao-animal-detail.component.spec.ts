import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Mamiferos2020TestModule } from '../../../test.module';
import { ObservacaoAnimalDetailComponent } from 'app/entities/observacao-animal/observacao-animal-detail.component';
import { ObservacaoAnimal } from 'app/shared/model/observacao-animal.model';

describe('Component Tests', () => {
  describe('ObservacaoAnimal Management Detail Component', () => {
    let comp: ObservacaoAnimalDetailComponent;
    let fixture: ComponentFixture<ObservacaoAnimalDetailComponent>;
    const route = ({ data: of({ observacaoAnimal: new ObservacaoAnimal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Mamiferos2020TestModule],
        declarations: [ObservacaoAnimalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ObservacaoAnimalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ObservacaoAnimalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load observacaoAnimal on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.observacaoAnimal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
