import dayjs from 'dayjs/esm';

import { IChat, NewChat } from './chat.model';

export const sampleWithRequiredData: IChat = {
  id: 24892,
  chatDate: dayjs('2022-08-16T05:16'),
  textMessage: 'metrics',
};

export const sampleWithPartialData: IChat = {
  id: 54319,
  chatDate: dayjs('2022-08-15T15:02'),
  textMessage: 'Assistant Ameliorated innovate',
};

export const sampleWithFullData: IChat = {
  id: 67515,
  chatDate: dayjs('2022-08-15T12:53'),
  textMessage: 'Martinique',
};

export const sampleWithNewData: NewChat = {
  chatDate: dayjs('2022-08-16T02:13'),
  textMessage: 'complexity invoice Baby',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
