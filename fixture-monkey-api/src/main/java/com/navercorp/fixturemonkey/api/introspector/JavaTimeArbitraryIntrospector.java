/*
 * Fixture Monkey
 *
 * Copyright (c) 2021-present NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.fixturemonkey.api.introspector;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import com.navercorp.fixturemonkey.api.generator.ArbitraryGeneratorContext;
import com.navercorp.fixturemonkey.api.matcher.Matcher;
import com.navercorp.fixturemonkey.api.property.Property;
import com.navercorp.fixturemonkey.api.type.Types;

@API(since = "0.4.0", status = Status.MAINTAINED)
public final class JavaTimeArbitraryIntrospector implements ArbitraryIntrospector, Matcher {
	private final Map<Class<?>, Function<ArbitraryGeneratorContext, ArbitraryIntrospectorResult>> introspector;

	public JavaTimeArbitraryIntrospector() {
		this(
			new JavaTimeTypeArbitraryGenerator() {
			},
			new JavaTimeArbitraryResolver() {
			}
		);
	}

	public JavaTimeArbitraryIntrospector(
		JavaTimeTypeArbitraryGenerator arbitraryGenerator,
		JavaTimeArbitraryResolver arbitraryResolver
	) {
		this.introspector = introspectors(arbitraryGenerator, arbitraryResolver);
	}

	@Override
	public boolean match(Property property) {
		Class<?> actualType = Types.getActualType(property.getType());
		return this.introspector.containsKey(actualType);
	}

	@Override
	public ArbitraryIntrospectorResult introspect(ArbitraryGeneratorContext context) {
		Class<?> type = Types.getActualType(context.getResolvedType());
		return this.introspector.getOrDefault(
				type,
				ctx -> ArbitraryIntrospectorResult.NOT_INTROSPECTED
			)
			.apply(context);
	}

	private static Map<Class<?>, Function<ArbitraryGeneratorContext, ArbitraryIntrospectorResult>> introspectors(
		JavaTimeTypeArbitraryGenerator arbitraryGenerator,
		JavaTimeArbitraryResolver arbitraryResolver
	) {
		Map<Class<?>, Function<ArbitraryGeneratorContext, ArbitraryIntrospectorResult>> introspector = new HashMap<>();

		introspector.put(
			Calendar.class,
			ctx -> new ArbitraryIntrospectorResult(
				arbitraryResolver.calendars(
					arbitraryGenerator.calendars(),
					ctx
				)
			)
		);

		introspector.put(
			Date.class,
			ctx -> new ArbitraryIntrospectorResult(
				arbitraryResolver.dates(
					arbitraryGenerator.dates(),
					ctx
				)
			)
		);

		introspector.put(
			Instant.class,
			ctx -> new ArbitraryIntrospectorResult(
				arbitraryResolver.instants(
					arbitraryGenerator.instants(),
					ctx
				)
			)
		);

		introspector.put(
			LocalDate.class,
			ctx -> new ArbitraryIntrospectorResult(
				arbitraryResolver.localDates(
					arbitraryGenerator.localDates(),
					ctx
				)
			)
		);

		introspector.put(
			LocalDateTime.class,
			ctx -> new ArbitraryIntrospectorResult(
				arbitraryResolver.localDateTimes(
					arbitraryGenerator.localDateTimes(),
					ctx
				)
			)
		);

		introspector.put(
			LocalTime.class,
			ctx -> new ArbitraryIntrospectorResult(
				arbitraryResolver.localTimes(
					arbitraryGenerator.localTimes(),
					ctx
				)
			)
		);

		introspector.put(
			ZonedDateTime.class,
			ctx -> new ArbitraryIntrospectorResult(
				arbitraryResolver.zonedDateTimes(
					arbitraryGenerator.zonedDateTimes(),
					ctx
				)
			)
		);

		introspector.put(
			MonthDay.class,
			ctx -> new ArbitraryIntrospectorResult(
				arbitraryResolver.monthDays(
					arbitraryGenerator.monthDays(),
					ctx
				)
			)
		);

		introspector.put(
			OffsetDateTime.class,
			ctx -> new ArbitraryIntrospectorResult(
				arbitraryResolver.offsetDateTimes(
					arbitraryGenerator.offsetDateTimes(),
					ctx
				)
			)
		);

		introspector.put(
			OffsetTime.class,
			ctx -> new ArbitraryIntrospectorResult(
				arbitraryResolver.offsetTimes(
					arbitraryGenerator.offsetTimes(),
					ctx
				)
			)
		);

		introspector.put(
			Period.class,
			ctx -> new ArbitraryIntrospectorResult(
				arbitraryResolver.periods(
					arbitraryGenerator.periods(),
					ctx
				)
			)
		);

		introspector.put(
			Duration.class,
			ctx -> new ArbitraryIntrospectorResult(
				arbitraryResolver.durations(
					arbitraryGenerator.durations(),
					ctx
				)
			)
		);

		introspector.put(
			Year.class,
			ctx -> new ArbitraryIntrospectorResult(
				arbitraryResolver.years(
					arbitraryGenerator.years(),
					ctx
				)
			)
		);

		introspector.put(
			YearMonth.class,
			ctx -> new ArbitraryIntrospectorResult(
				arbitraryResolver.yearMonths(
					arbitraryGenerator.yearMonths(),
					ctx
				)
			)
		);

		introspector.put(
			ZoneOffset.class,
			ctx -> new ArbitraryIntrospectorResult(
				arbitraryResolver.zoneOffsets(
					arbitraryGenerator.zoneOffsets(),
					ctx
				)
			)
		);

		return Collections.unmodifiableMap(introspector);
	}
}
