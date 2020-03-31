package milkman.plugin.scripting.nashorn;

import milkman.domain.RequestExecutionContext;
import milkman.plugin.scripting.ScriptOptionsProvider;
import milkman.ui.main.Toaster;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.script.*;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NashornExecutorTest {

    @Test @Disabled("no solution yet")
    void shouldUseGlobalScope(){
        NashornExecutor executor = new NashornExecutor(mock(Toaster.class));
        String out1 = executor.executeScript("console.log(testVar); var testVar = 'testValue';", null, null, new RequestExecutionContext(Optional.empty()));
        String out2 = executor.executeScript("console.log(testVar); var testVar = 'testValue';", null, null, new RequestExecutionContext(Optional.empty()));

        assertThat(out1).isEqualTo("undefined\n");
        assertThat(out2).isEqualTo("undefined\n");
    }

    @Test()
    void shouldUseChai(){
        ScriptOptionsProvider.options().setPreloadScripts(List.of("https://cdnjs.cloudflare.com/ajax/libs/chai/4.2.0/chai.js"));
        Toaster mock = mock(Toaster.class);
        NashornExecutor executor = new NashornExecutor(mock);

        executor.executeScript("chai.should(); ''.should.be.a('string')", null, null, new RequestExecutionContext(Optional.empty()));

        verify(mock, never()).showToast(anyString());
    }

    @Test()
    void shouldCarryOverChangesInGlobal() throws URISyntaxException {
        ScriptOptionsProvider.options().setPreloadScripts(List.of(getClass().getResource("/object-prototype-test.js").toURI().toString()));
        Toaster mock = mock(Toaster.class);
        NashornExecutor executor = new NashornExecutor(mock);

        String out = executor.executeScript("var x = {}; x.test('hello');", null, null, new RequestExecutionContext(Optional.empty()));

        verify(mock, never()).showToast(anyString());
        assertThat(out).isEqualTo("hello\n");
    }


    @Test @Disabled("no solution yet")
    void plainGlobalScopeTest() throws URISyntaxException, ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        Bindings globalBindings = engine.createBindings();
        engine.eval("Object.prototype.test = function(arg){print(arg);}", globalBindings);

        //works as expected, printing "hello"
        engine.getContext().setBindings(globalBindings, ScriptContext.ENGINE_SCOPE);
        engine.eval("var x = {}; x.test('hello');");

        //throws TypeError: null is not a function in <eval> at line number 1
        engine.getContext().setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
        engine.getContext().setBindings(globalBindings, ScriptContext.GLOBAL_SCOPE);
        engine.eval("var x = {}; x.test('hello');");

    }
}