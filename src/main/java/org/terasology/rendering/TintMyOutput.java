/*
 * Copyright 2017 MovingBlocks
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
package org.terasology.rendering;

import org.terasology.context.Context;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.rendering.dag.gsoc.ModuleRendering;
import org.terasology.rendering.dag.gsoc.NewNode;
import org.terasology.rendering.dag.nodes.TintNode;

// TODO NewAbstractNode to NewNode only!, no NewAbstractNode in here
// TODO Decide on what must by marked as @API or whitelisted (like nodes and such...use renderGraphAPI to access main DAG)
@RegisterSystem
public class TintMyOutput extends ModuleRendering {

    public TintMyOutput(Context context) {
        super(context);
    }

    public void initialise() {
        super.initialise();
        // TODO hide this..reflection?
           // super.setProvidingModule(this.getClass());
        moduleTintOutput();
    }

    private void moduleTintOutput() {
        // Create a new tintNode
        renderGraph.addShader("tint", providingModule);
        NewNode tintNode = new TintNode("tintNode", providingModule, context);
        // TODO connectEShader(distinguish between engine based and module based);

        // TODO nice to have, many possibilities of autonomous insertion
        // renderDagApi.insertBefore(tintNode, "engine:outputToScreenNode");

        renderGraph.disconnectOutputFboConnection("engine:finalPostProcessingNode", 1);
        renderGraph.reconnectInputFboToOutput("engine:finalPostProcessingNode", 1, tintNode, 1, true);
        renderGraph.addNode(tintNode);

        NewNode outputToScreenNode = renderGraph.findNode("engine:outputToScreenNode");
        renderGraph.reconnectInputFboToOutput("DagTestingModule:tintNode", 1, outputToScreenNode, 1, true);
        //outputToScreenNode.resetDesiredStateChanges();
    }
}
