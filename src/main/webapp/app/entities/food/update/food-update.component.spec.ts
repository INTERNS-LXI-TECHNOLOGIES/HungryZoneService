import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FoodFormService } from './food-form.service';
import { FoodService } from '../service/food.service';
import { IFood } from '../food.model';
import { ICategory } from 'app/entities/category/category.model';
import { CategoryService } from 'app/entities/category/service/category.service';
import { IUserExtra } from 'app/entities/user-extra/user-extra.model';
import { UserExtraService } from 'app/entities/user-extra/service/user-extra.service';

import { FoodUpdateComponent } from './food-update.component';

describe('Food Management Update Component', () => {
  let comp: FoodUpdateComponent;
  let fixture: ComponentFixture<FoodUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let foodFormService: FoodFormService;
  let foodService: FoodService;
  let categoryService: CategoryService;
  let userExtraService: UserExtraService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FoodUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(FoodUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FoodUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    foodFormService = TestBed.inject(FoodFormService);
    foodService = TestBed.inject(FoodService);
    categoryService = TestBed.inject(CategoryService);
    userExtraService = TestBed.inject(UserExtraService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Category query and add missing value', () => {
      const food: IFood = { id: 456 };
      const category: ICategory = { id: 4477 };
      food.category = category;

      const categoryCollection: ICategory[] = [{ id: 13567 }];
      jest.spyOn(categoryService, 'query').mockReturnValue(of(new HttpResponse({ body: categoryCollection })));
      const additionalCategories = [category];
      const expectedCollection: ICategory[] = [...additionalCategories, ...categoryCollection];
      jest.spyOn(categoryService, 'addCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ food });
      comp.ngOnInit();

      expect(categoryService.query).toHaveBeenCalled();
      expect(categoryService.addCategoryToCollectionIfMissing).toHaveBeenCalledWith(
        categoryCollection,
        ...additionalCategories.map(expect.objectContaining)
      );
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call UserExtra query and add missing value', () => {
      const food: IFood = { id: 456 };
      const donor: IUserExtra = { id: 54000 };
      food.donor = donor;

      const userExtraCollection: IUserExtra[] = [{ id: 29254 }];
      jest.spyOn(userExtraService, 'query').mockReturnValue(of(new HttpResponse({ body: userExtraCollection })));
      const additionalUserExtras = [donor];
      const expectedCollection: IUserExtra[] = [...additionalUserExtras, ...userExtraCollection];
      jest.spyOn(userExtraService, 'addUserExtraToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ food });
      comp.ngOnInit();

      expect(userExtraService.query).toHaveBeenCalled();
      expect(userExtraService.addUserExtraToCollectionIfMissing).toHaveBeenCalledWith(
        userExtraCollection,
        ...additionalUserExtras.map(expect.objectContaining)
      );
      expect(comp.userExtrasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const food: IFood = { id: 456 };
      const category: ICategory = { id: 92800 };
      food.category = category;
      const donor: IUserExtra = { id: 82088 };
      food.donor = donor;

      activatedRoute.data = of({ food });
      comp.ngOnInit();

      expect(comp.categoriesSharedCollection).toContain(category);
      expect(comp.userExtrasSharedCollection).toContain(donor);
      expect(comp.food).toEqual(food);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFood>>();
      const food = { id: 123 };
      jest.spyOn(foodFormService, 'getFood').mockReturnValue(food);
      jest.spyOn(foodService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ food });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: food }));
      saveSubject.complete();

      // THEN
      expect(foodFormService.getFood).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(foodService.update).toHaveBeenCalledWith(expect.objectContaining(food));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFood>>();
      const food = { id: 123 };
      jest.spyOn(foodFormService, 'getFood').mockReturnValue({ id: null });
      jest.spyOn(foodService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ food: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: food }));
      saveSubject.complete();

      // THEN
      expect(foodFormService.getFood).toHaveBeenCalled();
      expect(foodService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFood>>();
      const food = { id: 123 };
      jest.spyOn(foodService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ food });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(foodService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCategory', () => {
      it('Should forward to categoryService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(categoryService, 'compareCategory');
        comp.compareCategory(entity, entity2);
        expect(categoryService.compareCategory).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUserExtra', () => {
      it('Should forward to userExtraService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(userExtraService, 'compareUserExtra');
        comp.compareUserExtra(entity, entity2);
        expect(userExtraService.compareUserExtra).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
