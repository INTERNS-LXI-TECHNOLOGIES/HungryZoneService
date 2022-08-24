import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUserExtra, NewUserExtra } from '../user-extra.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUserExtra for edit and NewUserExtraFormGroupInput for create.
 */
type UserExtraFormGroupInput = IUserExtra | PartialWithRequiredKeyOf<NewUserExtra>;

type UserExtraFormDefaults = Pick<NewUserExtra, 'id' | 'chats'>;

type UserExtraFormGroupContent = {
  id: FormControl<IUserExtra['id'] | NewUserExtra['id']>;
  phoneNumber: FormControl<IUserExtra['phoneNumber']>;
  address: FormControl<IUserExtra['address']>;
  locationAtXAxis: FormControl<IUserExtra['locationAtXAxis']>;
  locationAtYAxis: FormControl<IUserExtra['locationAtYAxis']>;
  user: FormControl<IUserExtra['user']>;
  chats: FormControl<IUserExtra['chats']>;
};

export type UserExtraFormGroup = FormGroup<UserExtraFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UserExtraFormService {
  createUserExtraFormGroup(userExtra: UserExtraFormGroupInput = { id: null }): UserExtraFormGroup {
    const userExtraRawValue = {
      ...this.getFormDefaults(),
      ...userExtra,
    };
    return new FormGroup<UserExtraFormGroupContent>({
      id: new FormControl(
        { value: userExtraRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      phoneNumber: new FormControl(userExtraRawValue.phoneNumber, {
        validators: [Validators.required],
      }),
      address: new FormControl(userExtraRawValue.address, {
        validators: [Validators.required],
      }),
      locationAtXAxis: new FormControl(userExtraRawValue.locationAtXAxis, {
        validators: [Validators.required],
      }),
      locationAtYAxis: new FormControl(userExtraRawValue.locationAtYAxis, {
        validators: [Validators.required],
      }),
      user: new FormControl(userExtraRawValue.user),
      chats: new FormControl(userExtraRawValue.chats ?? []),
    });
  }

  getUserExtra(form: UserExtraFormGroup): IUserExtra | NewUserExtra {
    return form.getRawValue() as IUserExtra | NewUserExtra;
  }

  resetForm(form: UserExtraFormGroup, userExtra: UserExtraFormGroupInput): void {
    const userExtraRawValue = { ...this.getFormDefaults(), ...userExtra };
    form.reset(
      {
        ...userExtraRawValue,
        id: { value: userExtraRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): UserExtraFormDefaults {
    return {
      id: null,
      chats: [],
    };
  }
}