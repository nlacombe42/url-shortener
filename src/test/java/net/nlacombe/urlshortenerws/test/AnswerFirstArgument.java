package net.nlacombe.urlshortenerws.test;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class AnswerFirstArgument<T> implements Answer<T>
{
	@Override
	@SuppressWarnings("unchecked")
	public T answer(InvocationOnMock invocationOnMock)
	{
		return (T) invocationOnMock.getArguments()[0];
	}
}
